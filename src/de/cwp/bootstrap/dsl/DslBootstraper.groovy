package de.cwp.bootstrap.dsl

import com.github.rjeschke.txtmark.Processor
import de.lkv.basis.container.GenJavaBean
import de.lkv.basis.container.VarRaum
import de.lkv.basis.container.iface.IVarRaum
import de.lkv.basis.expressionparser.ExpressionParser
import de.lkv.basis.utils.StringBearbeiter
import de.lkvnrw.sql.factory.SQLFactory
import groovy.text.GStringTemplateEngine
import javafx.util.Pair

import static de.lkv.basis.utils.StringBearbeiter.splitteEinmal

class DslBootstraper {

/**
 * Created by paulsen on 27.09.16.
 * Klasse zum  Erstellen von Bootstrap-Htmlseiten mit Dartsupport
 */
    def actual = null;
    def title = "";
    def includestr="";
    def keywords = "";
    def description="";
    def progname,cssname ="";
    def stack = []
    def eventmeth = [:]
    def dartmeth = [:]
    def classes = [:]
    def links = [:]
    def tagnames = [:];
    int rc = 0;
    def rcmessage="";
    def isinclude= false;
    def htmlstr=null;
    def synonym = null;
    def htmlfilename= null;
    def dartoutput=false;
    VarRaum varraum = null;

    def start(param) {
     //   println "gestartet"
        actual = new ElementContainer()
        def props = getMap(param);
        actual.setProperties(props);
        actual.setName("div");
        actual.setClasses(classes)
        actual.setLinks(links)
    }
   def String printit(){
       println("----------------------------printit-----------------------------------------------------------------")
  if (links.isEmpty()) println("Achtung Links fehlen  ggf. linkfile filename setzen")
  if (classes.isEmpty()) println("Achtung Klassen fehlen  ggf. classfile filename setzen")
    if (tagnames.isEmpty()) println("Achtung Klassen fehlen  ggf. classfile filename setzen")
  includestr = actual.printit("");

  def htmlstr = null;
   if (isinclude) htmlstr = includestr;
   else if (htmlfilename!=null) {
           def cf = new File(htmlfilename);
           def htmlzstr = cf.text;
            def gstring = new GStringTemplateEngine()

       def gbinding = [title: title,includestr:includestr,cssname:cssname,progname:progname,keywords:keywords,description:description, gstring: true]
       htmlstr = gstring.createTemplate(htmlzstr).make(gbinding).toString()
    }

   else htmlstr =
  """
  <!doctype html>
  <html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="${description}">
    <meta name="keywords" content="${keywords}">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
     <link href="css/${cssname}.css" rel="stylesheet">
    <script async src="${progname}.dart" type="application/dart"></script>
    <script async src="packages/browser/dart.js"></script>
     <script src="js/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
     <style>
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      width: 70%;
      margin: auto;
  }
  </style>
</head>
<html>
   <body>

    ${includestr}

    </body>
 </html>

""";



 println("---------------------------printit END----------------------------------------------------------------")

      //  if (htmlstr.indexOf("${includestr}")> 0)htmlstr = htmlstr.replaceFirst('{includestr}',includestr);
        return htmlstr;

    }
 def int getRC() {
        return rc;

    }

   def String buildDart() {
 def dartstr = """
void main() {
  print("------> main");
  var htmlx = document.querySelector('#main');
  if (validator == null) {
    validator = new NodeValidatorBuilder()
      // ..allowCustomElement('A',attributes: ['href','data-target', 'data-toggle'])
      ..allowHtml5()
      ..allowSvg()
      ..allowNavigation(new MyUriPolicy())
       ..allowElement('object', attributes: ['data', 'style'])
      ..allowElement('img', attributes: ['src'])
      ..allowElement('button', attributes: ['data-tooltip'])
      // ..allowElement('a', attributes: ['href', 'data-target', 'data-toggle'])
      ..allowElement('input', attributes: [
        'pattern',
        'opt',
        'data-tooltip',
        'readonly',
        'disabled'
      ]);

  }
  generator = new UIGenerator();
  document.body.onClick.listen((e) {
    var clickedElem = e.target;
    var attr = clickedElem.attributes;
    attr.forEach((k, v) => print("clickelemattr \${k} \${v}"));
    String sid = clickedElem.id.toString();
    print('You clicked the \${clickedElem.id}- Element');
    if (clickedElem.id != null && sid.length > 0)
      populateAllClicks(e, clickedElem.id);
    else
      populateSvgClicks(e, clickedElem.text);
  });
}

void populateSvgClicks(Event e, String text) {
  print("SVG-TEXT:" + text);
}

void populateAllClicks(Event e, id) {
               String sid =id.toString();
"""

        dartstr += actual.buildDart();
       return dartstr;
   }

 def setDartOutput(param){
        if (param.equals("true") )
        this.dartoutput = true;
        else this.dartoutput = false;
    }

    def setVarRaum(VarRaum varraum){
        this.varraum = varraum;
    }
    def pushit(name,param){
    //    println "pushit ${name}     ${param}"
        stack.push(actual)
        def nactual  = buildElement(name,param)
        actual = nactual;
    }
    def buildElement(name,param) {
        ElementContainer nactual = new ElementContainer()
        nactual.setName(name);
        nactual.setClasses(classes)
        nactual.setLinks(links)
        if (!tagnames.isEmpty()) nactual.setNames(tagnames)
        if (param != null) {
           def props = getMap(param);
           nactual.setProperties(props);
    }
       // println nactual;
        if (actual == null) actual = nactual
        else {
            actual.add(nactual)
         //   nactual.setParentlist(actual.elemlist)
        }
        return nactual
    }
def container(param){
    pushit("container",param)
}
  def col(param){
   def params =  splitteEinmal(param, ";")
    def cols = params[0].split(":");
    pushit("col",params[1])
      def ind = 0;
      def colclass = "col "
    cols.each{

    }
}

def row(param){
    pushit("row",param)

}
def panel(param){
        pushit("panel",param)
 }
def panelhead(param){
    pushit("panelhead",param)
 }
 def panelfooter(param){
       pushit("panelfooter",param)
 }
def panelbody(param){
       pushit("panelbody",param)
 }
def media(param) {
    pushit("media", param)
}
  def img(param) {
     if (param.indexOf("§")>=0){
        param = param.replace("§","/service/getdocument/dbsynonym/${synonym}/");
     }
     def nactual = buildElement("img", param);
    }




 def table(param){
    pushit("table",param)

}
 def trth(param){
     pushit("tr",null);
     def ths = param.split(";")
     ths.each{
         def nactual = buildElement("th", null)
         nactual.setContent(it);
     }
     end("tr");
 }
    def trtd(param){
     pushit("tr",null);
     def tds = param.split(";")
     tds.each{
         if (it.startsWith('§')){
               def itx=it.substring(1)
               def gesplittet=splitteEinmal(itx,"_");
               def nactual = buildElement("td", null)
               String cont = "<button id='${itx}'> ${gesplittet[1]}</button>"
               nactual.setContent(cont);
         }
         else {
             def nactual = buildElement("td", null)
             nactual.setContent(it);
         }
     }
     end("tr");
 }

def in_password(param){
    def nactual= buildElement("in_password",param)
    nactual.setProperty("type","password")

}
def in_email(param){
    def nactual= buildElement("in_email",param)
    nactual.setProperty("type","email")
    }
  def textarea(param){

    }
def in_text(param){
    def nactual = buildElement("in_text",param)
    nactual.setProperty("type","text")
    }
 def label(param){
    buildElement("label",param)
    }
def div(param){
    pushit("div",param)
}
def include(param){
   // println "include ${param}"
    isinclude = new Boolean(param);
    // println isinclude
}

def body(param){
    pushit("body",param)
}
def formgroup(param){
    pushit("form-group",param)
}
  def formgroup(){
    pushit("form-group","info:test")
}
  def content(param){
        actual.setProperty("content",param)
    }
  def getMd(param){
      param = ExpressionParser.ersetzeDoubleCurly(varraum, param, false);
      def mdstr="";
      if (param.startsWith("§")){
        param = param.substring(1);
        def gesplittet = param.split(";");
       def synonym = gesplittet[0]

        def blogname = gesplittet[1]
         def thema = gesplittet[2]
         def sqlstr= "select * from nachricht where thema = '${thema}' and blogname ='${blogname}'"
         def erg = SQLFactory.selectFirstRow(synonym,sqlstr)
         mdstr = erg.getPropertyAsStr("nachricht");
      } else mdstr = param;
      if (varraum != null) mdstr = ExpressionParser.ersetzeDoubleCurly(varraum,mdstr,false);
      def result = Processor.process(mdstr);
      text(result);
  }
   def getMdExt(param){
      param = ExpressionParser.ersetzeDoubleCurly(varraum, param, false);
      def mdstr="";
     if (param.startsWith("§")){
        param = param.substring(1);
        def gesplittet = param.split(";");
        def titel = gesplittet[0]
       def synonym = gesplittet[1]
        def tablename = gesplittet[2]
        def keyfield = gesplittet[3]
        def keyvalue =  gesplittet[4]
        def mdfields = gesplittet[5];
          text(" <h2>${titel}</h2>");
         def sqlstr= "select * from ${tablename} where ${keyfield} = '${keyvalue}'"
         def erg = SQLFactory.selectFirstRow(synonym,sqlstr)
         def fields = mdfields.split(",");
          fields.each{field->
              mdstr = erg.getPropertyAsStr(field);
              if (varraum != null) mdstr = ExpressionParser.ersetzeDoubleCurly(varraum,mdstr,false);
              text("<div> <h3>${field}</h3><br/><pre>"+ Processor.process(mdstr) + "</pre></div>");
          }
       }
    }

 def getTexte(param){
      def mdstr="";
     param = ExpressionParser.ersetzeDoubleCurly(varraum, param, false);
     if (param.startsWith("§")) {
         param = param.substring(1);
         def gesplittet = param.split(";");
         def titel = gesplittet[0]
         def synonym = gesplittet[1]
         def tablename = gesplittet[2]
         def keyfield = gesplittet[3]
         def keyvalue = gesplittet[4]
         def mdfields = gesplittet[5];
         text(" <h2>${titel}</h2>");
         def sqlstr = "select * from ${tablename} where ${keyfield} = '${keyvalue}'"
         def resultlist = new ArrayList<GenJavaBean>();
         SQLFactory.selectMatrixAsList(synonym, sqlstr, resultlist);
         resultlist.each {erg ->
             println("ERG:${erg}");
             def fields = mdfields.split(",");
             text("<h2>${keyfield} - ${keyvalue}</h2>")
             fields.each { field ->
                 mdstr = erg.getPropertyAsStr(field);
                 println("${field} TEXTE:${mdstr}");
                 if (varraum != null) mdstr = ExpressionParser.ersetzeDoubleCurly(varraum, mdstr, false);
                 println("${field} TEXTE_EP:${mdstr}");
                 //  if (mdstr.indexOf("<")>0)
                 //  text("<div> <h3>${field}</h3><br/></textarea rows=\"8\" cols=\"80\">"+ mdstr + "</textarea>");
                 if (mdstr.indexOf("<") >= 0) mdstr = StringBearbeiter.str2csv(mdstr);
                 text("<div> <h3>${field}</h3><br/><pre><code>" + mdstr + "</code></pre>");
             }
         }
     }
    }
    def text(param){
        def cont = new ElementContainer()
        cont.setName("p");
        cont.setContent(param)
    actual.add(cont)
}
def setDescription(param){
    actual.setDescription(param)
    this.description = param;

}
def setTitle(param){

    this.title= param;

}
def setProgName(param){

    this.progname= param;

}
def setSynonym(param){

        this.synonym= param;

    }
def setCSS(param){

    this.cssname= param;

}
def setKeywords(param){
    this.keywords = param;

}
def setClassFile(param){
       def cf = new File(param);
       cf.splitEachLine(':') { items ->
           classes.put(items[0], items[1]);
       }
  }
   def setLinkFile(param){
       def cf = new File(param);
       cf.eachLine { line ->
           def items =   splitteEinmal(line,":");
           links.put(items[0], items[1]);
       }
  }
    def setHtmlFile(param){
        htmlfilename = param;

       }

    def setTagNameFile(param){
       def cf = new File(param);
       cf.splitEachLine(':') { items ->
           tagnames.put(items[0], items[1]);
       }
  }
    def setDartMeth(param){
       def cf = new File(param);
       cf.splitEachLine(':') { items ->
           dartmeth.put(items[0], items[1]);
       }
  }
   def setEventMeth(param){
       def cf = new File(param);
       cf.splitEachLine(':') { items ->
           eventmeth.put(items[0], items[1]);
       }
  }
    def addClass(param){
        def values = getMap(param.split(":"))
        classes.put(values[0],values[1]);
    }



    def css(param) {

    }
    def tabcontent(){
        pushit("tabcontent",null)
    }
    def tablecontent(param){
        def gesplittet = param.split(";");
        def synonym = gesplittet[0]
        def id = gesplittet[1]
        def idsplit = id.split("_");
        def rf = gesplittet[2].split(",")
        def sqlstr= gesplittet[3];
        def erglist = [];
        SQLFactory.selectMatrixAsList(synonym,sqlstr,erglist);

        erglist.each{
            GenJavaBean gjb = (GenJavaBean)it;

            def trtdstr ="";
            def seperator="";
            rf.each{
                if (it == idsplit[1]){trtdstr+="${seperator}§${idsplit[0]}_${gjb.getProperty(it)}"}
                else trtdstr+="${seperator}${gjb.getProperty(it)}"
                seperator=";"
            }
            println "trtd:${trtdstr}";
            trtd(trtdstr)
        }

    }




    def tabpaneactive(param){
          pushit("tabpaneactive",param)
    }
    def tabpane(param){
          pushit("tabpane",param)
    }

    def navbarimg(param) {
        def nactual = buildElement("navbarimg", param)
        def htmlstr = """
            <nav class="navbar navbar-default">
          <div class="container-fluid">
           <div class="navbar-header">
              """
        def elems = param.split(";");
        elems.each {
            htmlstr += """
              <a class="navbar-brand" href="#">
              <img alt="Brand" src="${it}">
                 </a>
          """
        }
      htmlstr += """
              < /div>
              </ div >
          """
        nactual.setProperty("content",htmlstr);

    }
    def iframe(param) {
        param = param.replace("§","/service/getdocument/dbsynonym/${synonym}/");
        def nactual = buildElement("iframe", param)
    }
    def buildNavTab(param) {
        def spliterg=splitteEinmal(param,":");
        pushit("navtab",spliterg[1]);
        def paramsplit= spliterg[0].split(";");
        def ind=0;
        def html1str="";
        paramsplit.each() {sp ->
            def its= sp.split('-');
            if (ind == 0)
               html1str+= """
                    <li class="active"><a data-toggle="tab" href="#${its[0]}">${its[1]}</a></li>
                """
                else html1str+= """
                    <li ><a data-toggle="tab" href="#${its[0]}">${its[1]}</a></li>
                """
            ind++;

        }

        actual.setProperty("content",html1str);

    }
     def navbar(param) {

        pushit("navbar",param);

    }

    def navbarbrand(param) {

        pushit("navbarbrand",param);

    }
    def navbarheader(param) {

        pushit("navbarheader",param);

    }
    def navbarbutton(param) {

        pushit("navbarbutton",param);

    }
    def navbarform(param) {

        pushit("navbarform",param);

    }


    def navbarcollapse(param) {

        pushit("navbarcollapse", param);
    }

     def navbarlist(param) {

         def spliterg=splitteEinmal(param,":");

        def  linklist= spliterg[0].split(";");
         spliterg =splitteEinmal(spliterg[1],":");
         def splitergdl= splitteEinmal(spliterg[0],"?");
       def dropdownlist= splitergdl[1].split(";");
         pushit("navbarlist",spliterg[1]);
        def html1str = "";
         def index = 0;
          linklist.each() {sp ->
            index++;
            def its= sp.split('-');
            if (index == 1)  html1str +=
        """
         <li class="active">
         <a href="#${its[0]}">${its[1]} <span class="sr-only">(current)</span></a></li>
        """
        else html1str +=
                    """
            <li><a href="#${its[0]}">${its[1]}</a></li>
            """
        }

        html1str +="""
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${splitergdl[0]} <span class="caret"></span></a>
          <ul class="dropdown-menu">
        """

          dropdownlist.each() {sp ->
            def its= sp.split('-');
            if (its[0] == "separator")
               html1str+= """
                   <li role="separator" class="divider"></li>
                """
                else html1str+= """
                    <li ><a  href="#${its[0]}">${its[1]}</a></li>
                """

        }
        html1str += """
           </ul>
         </li>
       """
        actual.setProperty("content",html1str);
      }
// builds sind hier complexere Methoden mit incl. Listen etc.
 def buildFooter1(param){
     param = param.replace("§","/service/getdocument/dbsynonym/${synonym}/");
     def spliterg=splitteEinmal(param,":");
     pushit("footer",spliterg[1]);
     pushit("container","style:padding-left: 0");
     pushit("row",null);
     pushit("col12",null);
     pushit("div","class:wrapper;style:background-color:#ecf0f1%semikolon padding: 1em 2em%semikolon");
      if (spliterg[0].indexOf("<>")>0){
           def subspliterg = splitteEinmal(spliterg[0],"<>");
           buildElement("h3","content:${subspliterg[0]}");
          buildAnkerList(subspliterg[1])
      }
      else  buildAnkerList(spliterg[0])
     end("div");
     end("col12");
     end("row");
     end("container");
     end("footer");
 }
    private void buildList(subspliterg) {
        pushit("ul", null);
        def eintraege = subspliterg.split(",");
        eintraege.each {
            buildElement("li","content:${it}")
        }
        end("ul");
    }
private void buildAnkerList(subspliterg) {
        pushit("ul", null);
        print "buildAnkerList ${subspliterg}"
        def eintraege = subspliterg.split(",");
        eintraege.each {
            pushit("li",null);
            buildElement("a","href:${it};it:${it};content:${it}");
            end("li");

        }
        end("ul");
    }

    def buildCarousel(param){
    def indicator="";
    def imgitems="";
    param = param.replace("§","/service/getdocument/dbsynonym/${synonym}/");
    def spliterg=splitteEinmal(param,":");
    def imgnames=null;

    def imgtexte = null;
        if (spliterg[0].indexOf("<>")>0){
            def subspliterg = splitteEinmal(spliterg[0],"<>");
            imgnames = subspliterg[0].split(",");
            imgtexte = subspliterg[1].split("|");

    }
        def werte= getMap(spliterg[1])
        def weite = werte.get("width")
        def hoehe = werte.get("height")
        werte.remove("width")
        werte.remove("height")
        def id = werte.get("id")
        pushit("carousel",spliterg[1])

     def ind = 0;
    imgnames.each(){

        if (ind == 0) {
            indicator += "<li data-target=\"#${id}\" data-slide-to=\"0\"  class=\"active\"></li>\n"
            imgitems+="""
     <div class="item active">
        <img src="${it}" alt="${it}" width="${weite}" height="${hoehe}">
      </div>


"""



        }
        else {
            indicator += "<li data-target=\"#${id}\" data-slide-to=\"${ind}\" ></li>\n"
 // <img src="${it}" alt="${it}" width="${weite}" hight="${hoehe}">
       imgitems+="""
     <div class="item">
        <img src="${it}" alt="${it}" width="${weite}" height="${hoehe}">
      </div>
      """
        }
        ind++
        }








    def carouselhtml="""

    <!-- Indicators -->
    <ol class="carousel-indicators">
    ${indicator}

    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
     ${imgitems}

    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#${id}" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#${id}" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>

"""

     actual.setProperty("content",carouselhtml)




    }

    def end(param) {
      // println "end ${param}"
        if (actual != null) {
            println "vor pop actual ${actual.getName()} ${actual}";
            actual = stack.pop();
            println "nach pop actual ${actual.getName()} ${actual}";
        } else println "!!!! Actual Null cannot be ended"
    }
    def buildtag(args){
       // println "buildtag ${args}"
        if (args.size()==1) buildElement(args[0],null)
        else buildElement(args[0],args[1]);
    }
    def buildit(args){
     //   println "buildit ${args}"
        if (args.size()==1) pushit(args[0],null)
        else pushit(args[0],args[1]);
    }
   def builditFromOrg(methname,args){
     //   println "builditFromOrg ${args}"
        if (args.size()==0) pushit(methname,null)
        else pushit(methname,args[0]);
    }


    def elseMeth(methname,args){
         println "In elseMeth";
          println "methname:${methname}"
          println args
        if (methname=='tag') buildtag(args);
        else  if (methname=='begin') buildit(args);
        else builditFromOrg(methname,args);
        println "In elseMeth";


    }
    /**
 * baut aus einem String mit Werten eine Map   Begrenzer : fuer KV-Paare und ; für die Paarentitaet
 * @param parammap
 * @return
 */
	def Map getMap(parammap){

		def pm = [:];

        if (parammap.length()== 0) return pm
        try {
            if (parammap != null) {
                def values = parammap.split(";");
                values.each {
                    def ats = splitteEinmal(it,":");
                    ats[1]= ats[1].replaceAll("%semikolon",";");
                     ats[1]= ats[1].replaceAll("%doppelpunkt",":");
                   // vorther def ats =  it.split(':');
                    pm[ats[0]] = ats[1];
                }
            }
        }catch(Exception e){println("Parameterfehler in ${parammap}:Prüfe die Trennzeichen Es sollten Semikolon und Doppelpunkt, kein Gleichheitszeichen verwendet werden ")}
		return pm;
	}
    def IVarRaum getVarRaum(){
        return varraum;
    }
/**
 * baut aus einem String eine Liste mit Key Value Paaren (incl. Wiederholungen)
 * @param params
 * @return
 */
    def List getList(params){

        def pl = [];


        def values = params.split(";");
        values.each{
            def ats = splitteEinmal(it,":");
           // def ats=it.split(':');
            ats[1]= ats[1].replaceAll("%semikolon",";");
            ats[1]= ats[1].replaceAll("%doppelpunkt",":");
            def p = new Pair(ats[0],ats[1]);
            pl.add(p);

        }
        return pl;
    }


}