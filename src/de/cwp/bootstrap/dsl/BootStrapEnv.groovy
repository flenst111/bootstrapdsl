package de.cwp.bootstrap.dsl;

/**
 * Created by paulsen on 20.09.16.
 */
import org.codehaus.groovy.control.CompilerConfiguration


public class BootStrapEnv {

	public static void main(String[] args){

		def compilerConfiguration = new CompilerConfiguration()
		compilerConfiguration.scriptBaseClass = BootstrapScript.class.name
		println('start');

		println "Scriptname  ${args[0]}"
		def outputfilename = args[1];
		def bootstraper = new DslBootstraper()

		// Add to script binding (CarScript references this.binding.car).
		def binding = new Binding(bootstraper: bootstraper)
		// Configure the GroovyShell.
		def shell = new GroovyShell(this.class.classLoader, binding, compilerConfiguration)
		def dsl4bootstraperDsl =  new File(args[0]).getText('UTF-8')
		// Run DSL script.
		shell.evaluate dsl4bootstraperDsl
		def outputfile = new File(outputfilename)
		outputfile.write(bootstraper.printit());
		if (bootstraper.dartoutput) {
			def dartfile = new File("${args[0]}.dart")
			dartfile.write(bootstraper.buildDart());
		}
		bootstraper.getRC();

	}
}