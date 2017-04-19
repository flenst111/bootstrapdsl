package de.cwp.bootstrap.dsl

import de.cwp.bootstrap.dsl.DslBootstraper
import de.cwp.bootstrap.dsl.BootstrapScript
import de.lkv.basis.container.BigBag
import de.lkv.basis.container.GenJavaBean
import de.lkv.basis.container.iface.IVarRaum
import de.lkv.basis.container.VarRaum
import de.lkv.basis.utils.HashBearbeiter
import de.lkv.basis.utils.LKVException
import de.lkv.runner.util.ParamGetter
import de.lkvnrw.sql.factory.SQLFactory
import de.lkvnrw.sql.metadata.ITableInfo
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.control.CompilerConfiguration
/**
 * Created by paulsen on 28.09.16.
 */
import org.codehaus.groovy.control.CompilerConfiguration;
abstract class BootstrapScript extends Script {
    int defid=0;
    def dartoutput(param){
        this.binding.bootstraper.setDartOutput(param)
     }

    def start(parammap) {
        println "start"
        this.binding.bootstraper.start(parammap)
    }
    def end(param) {
         println "end ${param}"
         this.binding.bootstraper.end(param)
    }

    def description(parammap) {
        println "description ${parammap}"
        this.binding.bootstraper.setDescription(parammap)
    }
    def setVarRaum(VarRaum varraum){
        this.binding.bootstraper.setVarRaum(varraum);
        this.varraum = varraum;
    }

    def text(param){
         println "text ${param}"
         this.binding.bootstraper.text(param)
    }
    def label(param){
         println "label ${param}"
         this.binding.bootstraper.label(param)
    }
    def textarea(param){
         println "textarea ${param}"
         this.binding.bootstraper.textarea(param)
    }
    def in_text(param){
         println "text ${param}"
         this.binding.bootstraper.in_text(param)
    }
    def in_email(param){
         println "text ${param}"
         this.binding.bootstraper.in_email(param)
        }
    def in_password(param){
         println "text ${param}"
         this.binding.bootstraper.in_password(param)
    }
    def classfile(param){
         println "classfile ${param}"
        this.binding.bootstraper.setClassFile(param)
    }
     def linkfile(param){
         println "linkfile ${param}"
        this.binding.bootstraper.setLinkFile(param)
    }
    def htmlfile(param){
         println "htmlfile ${param}"
        this.binding.bootstraper.setHtmlFile(param)
    }
    def synonym(param){
         println "synonym ${param}"
        this.binding.bootstraper.setSynonym(param)
    }
    def tagnamefile(param){
         println "tagnamefile ${param}"
        this.binding.bootstraper.setTagNameFile(param)
    }
    /**
     * Soll ein HTMLBODY etc aufgebaut werden? bei include false
     *
     * @param param
     * @return
     */

    def include(param){
         println "include ${param}"
        this.binding.bootstraper.include(param)
    }
    /** neuer Bootstraper wird aufgerufen Datei abgearbeitet
     * Datei in die HTML-Datei includiert
     *
     * @param param
     * @return
     */

    def incl(param){

         println "incl ${param}"
         def dsltext = null;

        if (param.startsWith("§")){
             param = param.substring(1);
             def selectstr = """
                  select * from nachricht where blogname="include" 
                  and thema = "${param}"
                """
             def select = SQLFactory.getSelect(synonym,sqlstr);
              GenJavaBean zeilenbean = select.getnextAsGJB();
              dsltext = zeilenbean.getProperty("nachricht");
         } else{

           dsltext =   new File(param).getText('UTF-8')
         }
          def compilerConfiguration = new CompilerConfiguration()
		 compilerConfiguration.scriptBaseClass = BootstrapScript.class.name
		 println("dsltext:${dsltext}");
		 def bootstraper = new DslBootstraper()
         bootstraper.setVarraum(this.binding.bootstraper.varraum);
		// Add to script binding (CarScript references this.binding.car).
		def binding = new Binding(bootstraper: bootstraper)
		// Configure the GroovyShell.
		def shell = new GroovyShell(this.class.classLoader, binding, compilerConfiguration)

        print "--------------------------------NEW Bootstraper INC --------------------------"
		def dsl4bootstraperDsl =  dsltext;
		// Run DSL script.
		shell.evaluate dsl4bootstraperDsl
        text(bootstraper.printit());
        if (bootstraper.dartoutput) {
			def dartfile = new File("${param}.dart")
			dartfile.write(bootstraper.buildDart());
		}

        print bootstraper.getRC();
        print "--------------------------------BACK FROM INC --------------------------"
    }

    def carousel(parammap) {
          println "carousel ${parammap}"
        this.binding.bootstraper.buildCarousel(parammap)
    }
    def navtab(parammap) {
          println "navtab ${parammap}"
        this.binding.bootstraper.buildNavTab(parammap)
    }
    def navbar(param) {
          this.binding.bootstraper.navbar(param)

    }
    def footer1(param) {
          this.binding.bootstraper.buildFooter1(param)

    }
  def navbarbrand(param) {
          this.binding.bootstraper.navbarbrand(param)

    }
  def navbarbutton(param) {
          this.binding.bootstraper.navbarbutton(param)
    }
    def navbarform(param) {
          this.binding.bootstraper.navbarform(param)
    }
    def navbarheader(param) {
          this.binding.bootstraper.navbarheader(param)
    }
    def navbarlist(param) {
          this.binding.bootstraper.navbarlist(param)
    }
   def navbarcollapse(param) {
      this.binding.bootstraper.navbarcollapse(param)
    }

    def navbarimg(parammap) {
          println "navbarimg ${parammap}"
        this.binding.bootstraper.navbarimg(parammap)
    }
    def img(parammap) {
          println "img ${parammap}"
        this.binding.bootstraper.img(parammap)
    }

    def keywords(parammap) {
         println "keywords ${parammap}"
        this.binding.bootstraper.setKeywords(parammap)
    }
    def form(parammap) {
        println "form ${parammap}"
        this.binding.bootstraper.buildform(parammap)
    }
    def css(param) {
        println "css ${param}"
        this.binding.bootstraper.setCSS(param)
    }

    def formgroup(parammap){
        this.binding.bootstraper.formgroup(parammap)

    }
     def content(param){
        this.binding.bootstraper.content(param)
    }
     def tabpane(param){
        this.binding.bootstraper.tabpane(param)
    }
    def tabpaneactive(param){
        this.binding.bootstraper.tabpaneactive(param)
    }
    def tabcontent(){
        this.binding.bootstraper.tabcontent()
    }

    def media(param){
         println "media ${param}"
        this.binding.bootstraper.media(param)
    }
    def md(param){
        println "getMd ${param}"
        this.binding.bootstraper.getMd(param)

    }

    def table(param){
         println "table ${param}"
        this.binding.bootstraper.table(param)
    }
     def tablecontent(param){
         println "tablecontent ${param}"
        this.binding.bootstraper.tablecontent(param)
    }
    def trtd(param){
        println "trtd ${param}"
        this.binding.bootstraper.trtd(param)
    }
    def trth(param){
        println "trth ${param}"
        this.binding.bootstraper.trth(param)
    }
    def panel(param){
        println "panel ${param}"
        this.binding.bootstraper.panel(param)
    }
    def panelhead(param){
        println "panelhead ${param}"
        this.binding.bootstraper.panelhead(param)
    }
    def panelbody(param){
        println "panelbody ${param}"
        this.binding.bootstraper.panelbody(param)
    }
    def panelfooter(param){
        println "panelfooter ${param}"
        this.binding.bootstraper.panelfooter(param)
    }
     def title(parammap) {
          println "title ${parammap}"
        this.binding.bootstraper.setTitle(parammap)
    }
    def formgroup(){
        this.binding.bootstraper.formgroup()

    }
    def row(parammap) {
        println "row ${parammap}"
        this.binding.bootstraper.row(parammap)
    }
      def body(parammap) {
           println "body ${parammap}"
        this.binding.bootstraper.body(parammap)
    }
    def end(){
         println "end"
        this.binding.bootstraper.end()
    }
    def invokeMethod(String name, Object args) {
                 try {
                       println "MethName:${name}"
                       println "Args ${args}"

                        this.binding.bootstraper.elseMeth(name,args)
                 } catch (MissingMethodException mme) {
                         return
                 }
         }

}
