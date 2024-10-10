/* groovylint-disable CompileStatic */
Integer test = 5
String strTest = "$test"
println(strTest)

def echo(String text) {
    println("${text}")
} 

String string1 = "doof"
String string2 = 'doofpot'

//if (string1 === string2) {echo "JEEJ ${string1} is gelijk aan ${string2}"}

public class Test {
    private final String een;
    private final String twee;
    

    Test(String een,String twee){

        this.een = een;
        this.twee = twee;
        //this.result = result;

        //return this
    }  

    private def echo(String text) {
    println("${text}");
    }
    
    public def createConfigJson(Map configMap) {
        def jsonBuilder = new StringBuilder().append("{\n")

        configMap.each { key, value ->
            jsonBuilder.append("  \"$key\": \"$value\",\n")
        }

        // Delete last ',' instead of the newline
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 2)
        jsonBuilder.append("}")
        return (jsonBuilder.toString());
    }

    public def compare()
    {
        Map result = ['this.een':"${this.een}"]
        result.put('operator','==')
        result.put('this.twee',"${this.twee}")
        if(this.een  == this.twee ){
            result.put('resultaat', "this.een == ${this.twee}");
            echo("${this.een} == ${this.twee}");
        } else {
            result.put('resultaat', "this.een != ${this.twee}");
            echo("${this.een} != ${this.twee}");
        }
        if(this.een  === this.twee ){
            result.put('resultaat', "this.een === ${this.twee}");
            echo("${this.een} === ${this.twee}");
        } else {
            result.put('resultaat', "this.een !== ${this.twee}");
            echo("${this.een} !== ${this.twee}");
        
        //def resultb = result
        
        def resultb = result
         if(result == resultb){echo "result: ${result} == resultb: ${resultb}"}
        if(result === resultb){echo "result === resultb"}
        String resultJSonString = createConfigJson(result)
        return resultJSonString
    }
    
    def objectList = ['object1':"${this.een}"]
    //objectList["object1"] = this.een
   // objectList.put(['object2',"${this.twee}"])
   // objectList["result"] = this.result
    
    }
}

def testObj = new Test(string1,string2)
def testObj2 = new Test(string1,string2)
String resultaat = testObj.compare()

//println("${resultaat}")