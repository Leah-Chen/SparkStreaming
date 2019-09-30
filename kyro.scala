/*
 * Use Kryo serialization and register the given set of classes with Kryo.
 * If called multiple times, this will append the classes from all calls together
 */

 def registerKryoClasses(classes: Array[Class[_]]):SparkConf = {
 	val allClassNames = new LinkedHashSet[String]()
 	allClassNames ++= get("spark.kryo.classesToRegister","").split(',').map(_.trim)
 	.filter(!_.isEmpty)
 	allClassNames ++= class.map(_.getName)

 	set("spark.kryo.classesToRegister",allClassNames.mkString(","))
 	set("spark.serializer",classOf[KryoSerializer].getName)
 	this
 }
