    package de.cwp.bootstrap.dsl

    import de.lkv.basis.container.GenJavaBean

    /**
     * Created by paulsen on 28.09.16.
     */
    class ElementContainer extends GenJavaBean{
        def elemlist = []
        def parentlist= []
        def name = "";
        def extname= null;
        def endname = null;
        def header = null;
        def footer = null;
        def cssclasses = [:]
        def links = [:]
        def names =['row': 'div', 'container':'div','start':'html','carousel':'div',
                    'col': 'div',
                    'col1': 'div',
                    'col2': 'div',
                    'col3': 'div',
                    'col4': 'div',
                    'col5': 'div',
                    'col6': 'div',
                    'col7': 'div',
                    'col8': 'div',
                    'col9': 'div',
                    'col10': 'div',
                    'col11': 'div',
                    'col12':'div'];
        def description = null;
        def title = null;
        def content = null;
        def add(elem){
            elemlist.add(elem);
        }
        def setClasses(classes){
            this.cssclasses = classes;
        }
         def setLinks(links){
            this.links = links;
        }
        def getElemList(){
            return elemlist;
        }
        def setParentList(parentlist){
            this.parentlist=parentlist;
        }
        def setNames(znames){
            names = names + znames
        }
        def setName(zname){
            name = zname
        }
        def getName(){
            return name;
        }
        def String printit(prefix){


         //   println "${name}"
            if (extname == null)extname = name;
            def printstr ="\n${prefix}"
            extname = names.get(name,name);
            printstr+= "<${extname} "
            def attr = getProperties();
            if (attr.get("content")!=null){
                content = attr.get('content');
                attr.remove('content');
            }
            boolean classattrda=false;
            for ( item in attr ){
           //     println item
               def key = item.key;
                def value = item.value
                if (key == 'href'){
                   // println ("LINKVAL:${value}");
                   // println ("Possible links: ${links}");
                    if  (links[value]!=null) value =  links[value];
                    else value = "#${value}"
            }
                if (key == 'class') {
                        classattrda = true;
                        printstr += "class='";
                        if (cssclasses[name]!= null) printstr += cssclasses[name] + " ";
                        def values = value.split(' ');
                        def nvalue = "";
                        for ( tok in values ) {
                            if (cssclasses[tok] != null) nvalue += cssclasses[tok];
                            else nvalue += tok;
                        }
                        printstr += nvalue + "'"
                    }
                else printstr += " ${key}='${value}'"

            }

            if (!classattrda){
                if (cssclasses[name]!=null)printstr += " class='${cssclasses[name]}' ";
            }
            printstr+=">"
            prefix += "   "
            if (content != null) printstr+= content;

            elemlist.each{
                printstr+= it.printit(prefix)
            }
            printstr += prefix + "</${extname}>\n "
            return printstr
        }
        def String buildDart(){
            println "${name}"
          //   println "PARENTLIST:";
          //    println parentlist

            def attr = getProperties();
            //println("Attribute ${attr}")
            if (extname == null)extname = name;
            def printstr = "";
            extname = names.get(name,name);
            if (extname.equals('button')){
                if (attr.get('id')!=null) {
                    def id= attr.get('id')
                    def id_teile = id.split("_");
                    if (id_teile.size()<2) println("WARN BITTE EVENT_IDS immer mit underscore in zwei Teile trennen")
                    printstr = """
                    if (sid == "${id}") {
                    var sts = id.split('_');
                    var nrid = sts[1];
                    expandid = nrid;
                    startEventFunction${id}(${id});
    }
    """
             if (id.startsWith("form")){






                      def formmethstr=""" startEventFunction${id}(){
                  String elements = '"""
                 def limiter="";
                        parentlist.each{
                           // println("PAR:${it.getName()}")
                            def pid = it.getProperty("id");

                         //   println("PARPID:${pid}");
                            if (it.getName().startsWith("in_")){
                                 formmethstr+="${limiter}inp=${pid}"
                                 limiter=",";
                            } else if (it.getName().equals("textarea")){
                                   formmethstr+="${limiter}ta=${pid}"
                                   limiter=",";
                            }
                         }
                         formmethstr+="""'
                            var vmap;
                            vmap = generator.getMaskenWertebyFieldId(elements,'hex');
                            print(vmap);
                            vmap['tablename']=tabellenname;
                            vmap['synonym']=dbsynonym;
                            vmap['dbsynonym']=dbsynonym;
                            vmap = generator.addConstWerte('${id_teile[1]}nr',localStorage,host,ext,vmap);
                            print(" Die VMAP: \${vmap}");
                            String uedata = encodeMap(vmap);

                            var req = new HttpRequest();
                            req.open('post','/\${host}/service', async: false);
                            //  req.setRequestHeader('Content-type', 'application/x-form-urlencoded');
                            req.send(uedata);
                            generator.setMessage('<h2>UEBERTRAGEN</h2>','#footer');
                            }
                             String encodeMap(Map data) {
                                return data.keys.map((k) {
                                     return '\${Uri.encodeComponent(k)}=' +  '\${Uri.encodeComponent(data[k])}';
                                 }).join('&');
}
                            
                            
                            
                            
""";
                         def dartfile = new File("${id}.dart")
                         dartfile.write(formmethstr);

                    }





                   }

            }
           if (elemlist!=null) elemlist.each{
                it.setParentList(getElemList());
                printstr+= it.buildDart()
            }
            return printstr
        }
    }
