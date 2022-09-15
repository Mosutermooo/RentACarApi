package com.example.utils

import com.example.service.RentCarService
import com.example.utils.Constants.JOB_MAP_CarId_ID_KEY
import com.example.utils.Constants.JOB_MAP_UPDATE_IN_ID_KEY
import kotlinx.coroutines.Dispatchers
import org.quartz.Job
import org.quartz.JobExecutionContext

class RentJob(
    private val rentCarService: RentCarService
) : Job {
    override fun execute(context: JobExecutionContext?) {
        context ?: return

        val dataMap = context.jobDetail.jobDataMap
        val carId = dataMap.getInt(JOB_MAP_CarId_ID_KEY)
        val updateIn = dataMap.getLong(JOB_MAP_UPDATE_IN_ID_KEY)

        rentCarService.updateCarStatus(carId, updateIn)

    }
}