import org.blinkenlights.jid3._;
import org.blinkenlights.jid3.v1._;
import org.blinkenlights.jid3.v2._;
import java.io._
import java.util.regex._

package audio { 


object main {
  def main(args: Array[String]) {
    val adf = new AudioDuplicateFinder
    var list: List[File] = List[File]()
    var file = new File("/home/gary/libs/")
    list = adf.recurseGetFiles(file, list)
    
    println("start")
    for( a <- list) {
      println(a.getName)
      val oMediaFile = new MP3File(a);
      val id3tag = oMediaFile.getID3V2Tag
      if (id3tag != null)
    	  println(id3tag.toString)
    }
    println("end")
  }
}


class AudioDuplicateFinder() {
  //var fileList:Array[AudioFile]
 
  def recurseGetFiles(file: File, fileList: List[File]) : List[File] = {
    var mutable = fileList
    var p = Pattern.compile(".*(mp3|aac|wav)")
    
    if (file.isDirectory()) {
      for (a <- file.list) {
        var f = new File(file.getPath + "/" + a)
         mutable = recurseGetFiles(f, fileList) ++ mutable
         
      }
    }
    else {
      var m = p.matcher(file.getName)
      if (m.matches)
        mutable = file :: mutable       
        
    }

  	return mutable
  	}
  

      
  }
}

case class AudioFile(path: String, hash: String, audioTags: ID3V2_3_0Tag, fileSize: Int)

