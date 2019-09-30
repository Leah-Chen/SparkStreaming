val conf = HBaseConfiguration.create()
Dstream.foreachRDD(rdd =>){
	rdd.foreachPartition(Records =>){
	val table = new HTable(conf,"data1")
	insertHbase(table, Records)
	sendToKafka("myTopic", Records)
	}
}

def insertHbase(table:HTable, records:Iterator[(String,String)]):Unit = {
	val puts = new ArrayList[Put]()
	try{
		records.foreach(record =>){
			val put = new Put (Bytes.toBytes("rowkey"))
			put.add(Bytes.toBytes("columnFamily"),Bytes.toBytes("qualifier"),Bytes.toBytes(record._1 +record._2))
			put.add(put)
		}
		Try(table.put(puts)).getOrElse(table.close())
	} catch {
		case e.Exception =>
			e.printStackTrace()
	}
}