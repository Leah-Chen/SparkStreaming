ssc.addStreamingListner(new StatReporter(appName, loggerUrl))

class StatReporter(appName: String, metricsUrl:String) extends StreamingListener{
	private[this] val logger = Logger.getLogger(getClass().getName())
	override def onBatchCompleted(batch: StreamingListnerBatchCompleted) = {
		def doSend(metricName: String, metricValue: String) ={
			// send email
		}

		if (batch.batchInfo.numRecords > 0){
			doSend("processing-delay"), batch.batchInfo.processingDelay.get.toString())
			doSend("scheduling-delay"), batch.batchInfo.schedulingDelay.get.toString())
		}
	}
}