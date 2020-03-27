package com.lifeinide.lanterna.service

import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Allows to execute tasks in the background thread pool.
 *
 * @author Lukasz Frankowski
 */
object ExecutorService {

    private val cancellableExecutor = Executors.newFixedThreadPool(4)
    private var currentJobs = mutableMapOf<String, Future<*>>()

    /**
     * Executes single job. Cancels previous job with the same name if is in progress or in delay mode.
     *
     * @param jobName Give if you want to be able to cancel the job with another job with the same name.
     * @param delay Delay before the job is triggered. This is useful if you plan to cancel this job before it starts.
     * @param f The job.
     */
    fun execute(jobName: String? = null, delay: Long = 0, f: () -> Unit): Future<*> {
        if (jobName!=null)
            currentJobs[jobName]?.apply {
                if (!isDone && !isCancelled) {
                    Logger.log("Cancelling job: $jobName")
                    cancel(true)
                }
            }

        Logger.log("Submitting job: $jobName")
        val future = cancellableExecutor.submit {
            if (delay>0) {
                Logger.log("Delaying job: $jobName by $delay ms")
                Thread.sleep(delay)
            }
            try {
                Logger.log("Executing job: $jobName")
                ThrobberService.start()
                f()
                Logger.log("Finishing job: $jobName")
            } catch (e: Exception) {
                Logger.log("Job finished with error: $jobName", e)
            } finally {
                ThrobberService.stop()
            }
        }

        if (jobName!=null)
            currentJobs[jobName] = future

        return future
    }

    fun done() {
        currentJobs.values.forEach {
            if (!it.isDone && !it.isCancelled)
                it.cancel(true)
        }

        cancellableExecutor.shutdown()
    }

}
