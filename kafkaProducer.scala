import org.apache.kafka.client.producer.{KafkaProducer,ProducerRecord}

class KafkaSink(createProducer:() => KafkaProducer[String, String]) extends Serializable {
	lazy val producer = createProducer()
	def send(topic:String, value:String):Unit = produer.send(new ProducerRecord(topic,value))
}

object KafkaSink{
	def apply(config: java.util.Properties): KafkaSink = {
		val f = () => {
			val producer = new KafkaProducer[String, String](config)
			sys.addShutdownHook{
				producer.close()
			}
			producer
		}
		new KafkaProducer
	}
}

/* easy to use */
val KafkaSink = ssc.sparkContect.broadcast(KafkaSink(KafkaProducerConfig))
Dstream.foreachRDD{ rdd =>
	rdd.foreachPartition{iter =>
		iter.foreach(record => {
			KafkaSink.value.send("topic","message")
		})
	}
}