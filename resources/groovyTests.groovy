/* groovylint-disable CompileStatic */

def echo(String text) {
    println("${text}")
} 

String string1 = "doof"
String string2 = 'doof'

//if (string1 === string2) {echo "JEEJ ${string1} is gelijk aan ${string2}"}

public class Test {
    private final String een;
    private final String twee;
    private Map result = [];
    
    Test(String een,String twee){

        this.een = een;
        this.twee = twee;
        this.result = result;

        //return this
    }  

    private def echo(String text) {
    println("${text}");
    }²
    
    public def createConfigJson(Map configMap) {
        def jsonBuilder = new StringBuilder().append("{\n")

        configMap.each { key, value ->
            jsonBuilder.append("  \"$key\": \"$value\",\n")
        }

        // Delete last ',' instead of the newline
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 2)
        jsonBuilder.append("}")
    }

    public def compare()
    {
        if(this.een  == this.twee ){
            this.result << "${this.een} == ${this.twee}";
            echo("${this.een} == ${this.twee}");
        } else {
            this.result << "${this.een} != ${this.twee}";
            echo("${this.een} != ${this.twee}");
        }
        if(this.een  === this.twee){
            this.result << "${this.een} === ${this.twee}";
            echo("${this.een} === ${this.twee}");
        } else {
            this.result << "${this.een} !== ${this.twee}";
            echo("${this.een} !== ${this.twee}");
        }
        def resultb = result
        if(this.een  === this.twee){
            this.result << "resultb === this.result";
            echo("${this.een} === ${this.twee}");
        }
        String resultJSonString = createConfigJson(this.result)
        return resultJSonString
    }
    
    def objectList = ['object1':"${this.een}"]
    //objectList["object1"] = this.een
   // objectList.put(['object2',"${this.twee}"])
   // objectList["result"] = this.result
    
}

def testObj = new Test(string1,string2)

testObj.compare()

