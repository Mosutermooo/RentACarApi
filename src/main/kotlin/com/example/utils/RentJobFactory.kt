package com.example.utils

import com.example.service.RentCarService
import org.quartz.Job
import org.quartz.Scheduler
import org.quartz.spi.JobFactory
import org.quartz.spi.TriggerFiredBundle
import kotlin.reflect.jvm.jvmName

class RentJobFactory(
    private val rentCarService: RentCarService
) : JobFactory {
    override fun newJob(bundle: TriggerFiredBundle?, scheduler: Scheduler?): Job {
        if(bundle != null){
            val jobClass = bundle.jobDetail.jobClass
            if (jobClass.name == RentJob::class.jvmName) {
                return RentJob(rentCarService)
            }
        }
        throw NotImplementedError("Job factory error")
    }
}