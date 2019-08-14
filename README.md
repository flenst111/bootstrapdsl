## BOOTSTRAPDSL a BOOTSTRAP and HTML based DSL, which produces HTML-Sites from DSL-Scripts

This a small BOOTSTRAP-GROOVY-DSL-Script which can produce your websites. For some functions it uses the GLKVRUNNER from the LKV NRW. But I will also develop a branch without the GLKVRUNNER, 
if somebody wants to use this DSL. One advantage of the glkvrunner is that you can produce HTML-Sites with dynamic content on the fly with the REST-based GSERVICESERVER, which I produced based on the glkvrunner. 
I use DART for webapp-development. BOOTSTRAPDSL is developed under LGPL3, but the glkvrunner not. So it can also produce template-files for the dart-app.

### USAGE

#### Put the tagnames into a file and change the content up to your targets

```
navtabs:ul
buttongroup:div
password:input type="password" 
in_text:input
in_password:input
in_email:input
form-group:div
tabcontent:div
tabpaneactive:div
tabpane:div
navbarimg:nav
navbar:nav
navbarheader:div
navbarbrand:a
spansr:span
spanicon:span
navbarbutton:button
containerfluid:div
container:div
navbarcollapse:div
navbarlist:ul
list:li
navbarbrand:a
navbarform:form
panel:div
panelhead:div
panelbody:div
panelfooter:div
media:div
mediabody:div
caption:div
label:span
glyphicon:span
well:div
badge:span
```

#### Put the classes into a classfile and change the content up to your targets

```
col12:col-xs-12 col-sm-6 col-md-12 col12 col
col8:col-xs-8 col-sm-6 col8 col-md-8 col
col11:col-xs-11 col-sm-6 col11 col-md-11 col
col10:col-xs-10 col-sm-6 col10 col-md-10 col
col9:col-xs-9 col-sm-6 col9 col-md-9 col
col7:col-xs-7 col-sm-4 col7 col-md-7 col
col6:col-xs-6 col-sm-3 col6 col-md-6 col
col5:col-xs-5 col-sm-3 col5 col-md-5 col
col4:col-xs-4 col-sm-2 col8 col-md-4 col
col3:col-xs-3 col-sm-1 col3 col-md-3 col
col2:col-xs-2 col-sm-1 col2 col-md-2 col
col1:col-xs-1 col-sm-1 col1 col-md-1 col
row:row
nav:nav
navtab:nav-tabs nav
container:container
nav-pills:nav nav-pills
alertinfo:alert alert-info
alert:alert alert-info
alertlink:alert-link alert
buttongroup:btn-group
buttongroupjustified:btn-group btn-group-justified
button:btn btn-primary
carousel:carousel slide
in_password:form-control
in_text:form-control
in_email:form-control
textarea:form-control
form-group:form-group
tabcontent:tab-content
tabpaneactive:tab-pane fade in active
tabpane:tab-pane fade
navbarimg:navbar navbar-default
navbar:navbar navbar-inverse navbar-fixed-top
navbarbrand:navbar-brand
navbarheader:navbar-header
spansr:sr-only
spanicon:icon-bar
navbarbutton:navbar-toggle collapsed
cointainerfluid:container-fluid
container:container
navbarcollapse:collapse navbar-collapse
navbarlist:nav navbar-nav
navbarbrand:navbar-brand
navbarform:navbar-form navbar-left
table:table table-bordered
tr:active
th:active
td:info
panel:panel panel-info
panelhead:panel-heading
panelbody:panel-body
panelfooter:panel-footer
media:media
a:pull-left
img:media-object
mediabody:media-body
caption:caption
label:label label-primary
```
#### Linkfile

For example
```
home:http://leever-miteenanner.de
twitter:http://twitter.com
despora:http://despara.com
google:http://google.de
mitmachcafe:http://leever-miteenanner.de/mitmachcafe.html
radtouren:http://leever-miteenanner.de/radtouren.html
```
#### Templatefile 

```
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap Navbar Example</title>
<!-- Bootstrap -->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  




 <style>
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      width: 70%;
      margin: auto;
  }
  </style>
</head>
<body>
  
${includestr}
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>

```

### Examples

#### First
```
include "false"
def button="button"
dartoutput "true"
start "test:xyz;spaß:xxx"
tagnamefile "/team/home/test/dsl4bootstrap/tagnames.txt"
classfile "/team/home/test/dsl4bootstrap/classes.txt"
title "auschring Süder"
description """Tauschring Süderbrarup, Hand in Tand tauschen mit Talenten
"""
idee ("von","test")
keywords "Tauschring,Süderbrarup,Talente"
body "class:yyy"
begin ("row","class:zxy4 3322")
begin ("col12")
begin ("navbar", "")
navbarheader ""
tag ("navbarbrand","href:#;content:Tauschring")
end "div"
navbarcollapse "id:bs-example-navbar-collapse-1"
navbarlist ("Information-Information;Neues-Neues;Marktzeitung-Marktzeitung;Angebote-Angebote:Mehr?Aktion-Aktion;Links-Links:id:navbarlist")
end "navbarlist"
end "xxx"
end "navbar"
end ("col12")
end ("row")
begin ("row","class:zxy4 3322")
begin ("col12")
text ''' <br/><br/>'''
end ("col12")
end ("row")
begin ("container","class:zzzz")
begin ("row","class:zxy4 3322")
begin ("col3")
text """
Ein Mensch ist <b>mehr</b> als seine Ausbildungen, mehr als sein Beruf. <b>Jeder Mensch hat viele Talente</b>, die er oft nur selten nutzt, da in unserer heutigen Welt nur das, was Geld bringt, auch wichtig und sinnvoll erscheint. Im Tauschring kann man seine Talente einsetzen und kann dafür andere Talente nutzen. Keine Unterordnung, kein Herabschauen, sondern gleichberechtigte Tauschpartnerschaft auf der Grundlage von Talenten, das ist die Crux.
"""
end ("col3")
begin ("col6")
carousel "tr1.jpg,tr2.jpg,tr3.jpg,tr4.jpg,tr5.jpg,trsueder.jpg<>Tauschen1|Tauschen2|Tauschen3|Tauschen4|tauschen5|Tauschen6:data-ride:carousel;id:myCarousel;width:400;height:300"
end "carousel"
end ("col6")
begin ("col3")
text """
<b>
<ul>
<li>Sie brauchen einen Babysitter?</li>
<li>Sie brauchen Hilfe im Garten? </li>       
<li>Sie suchen jemand, der Näharbeiten übernimmt?</li>
<li>Sie könnten etwas Unterstützung im Haushalt gebrauchen?</li>
<li>Sie brauchen jemand, der “sein Handwerk versteht”?</li>
<li>Sie brauchen jemand, der ihre Einkäufe nach Hause bringt?</li>
<li>Sie sind es leid immer nur alleine Fertiggerichte zu essen.</li>
<li>Sie wünschen sich ein leckeres Essen “wie bei Mutter zu Hause” usw.</li>
<li>Sie brauchen für den Kaffeetisch einen selbstgebackenen Kuchen!</li>
</ul>
</b>
Dann sind Sie bei uns genau richtig ! Wenn auch Sie bereit sind anderen zu helfen, nette Kontakte knüpfen und ihren Alltag erleichtern wollen, dann freuen wir uns auf Sie.Dies ist ein längerer Text, den wir so noch nicht gesehen haben
"""
end ("col3")
end "row"
incl "./test.inc"
incl "./test2.inc"
end "container"
end "body"
```

#### test.inc
```
include "true"
def button="button"
dartoutput "true"
start "test:xyz;spaß:xxx;id=panels"
tagnamefile "/team/home/test/dsl4bootstrap/tagnames.txt"
classfile "/team/home/test/dsl4bootstrap/classes.txt"
begin ("row","class:1234")
begin ("col3", "class:test")
panel "id:hoop"
panelhead "id:work"
content "Maitag in Süder"
end "panelhead"
panelbody "id:work1"
content """ In Süderbrarup findet Jahr für Jahr der Maitag statt. Dabei sind wir dies Jahr leider nicht,
aber geht hin, schaut hin, es <b>lohnt</b> sich.
"""
end "panelbody"
panelfooter "id:xxx"
tag ("button","content:Link zur Seite;id:btn_more")
end "panelfooter"
end "panel"
end "col3"

begin ("col3", "class:test")

panel "id:hoop"
panelhead "id:work"
content "Die singende Revolution in Estland"
end "panelhead"
panelbody "id:work1"
content """ Unser nächstes Treffen findet am 25.4 um 18:30 wie immer im Familienzentrum statt. Nach Tausch und Plausch startet unser Filmabend mit
 <b>Die singende Revolution in Estland</b>
"""
end "panelbody"
panelfooter "id=xxx"
tag ("button","content:Mehr;id:btn_singmehr")
tag ("button","content:Hintergrundinfos;id:btn_back")
end "panelfooter"
end "panel"
end "col3"
begin ("col3", "class:test")
panel "hoop"
panelhead "id:work"
content "Noch was"
end "panelhead"
panelbody "id:work1"
content """ Hier kommt der ganze Content
auch über mehrere Zeilen
ich glaube dran, das das <b>funktioniert</b>"""
end "panelbody"
panelfooter "id:xxx"
tag ("button","content:Button1;id:btn_funct")
tag ("button","content:Button2;id:btn_funct2")
end "panelfooter"
end "panel"
end "col3"
begin ("col3", "class:test")
panel "id:hoop"
panelhead "id:work"
content "Noch was"
end "panelhead"
panelbody "id:work1"
content """ Hier kommt der ganze Content
auch über mehrere Zeilen
ich glaube dran, das das <b>funktioniert</b>"""
end "panelbody"
panelfooter "id:xxx"
tag ("button","content:Link1;id:btn_cont")
tag ("button","content:Link2;id:btn_cont4")
end "panelfooter"
end "panel"
end "col3"
end "row"
```
#### second
```
include "true"
def button="button"
dartoutput "true"
start "test:xyz;spaß:xxx;id=panels"
tagnamefile "/team/home/test/dsl4bootstrap/tagnames.txt"
classfile "/team/home/test/dsl4bootstrap/classes.txt"
begin ("row","class:1234")
begin ("col3", "class:test")
panel "id:hoop"
panelhead "id:work"
content "Maitag in Süder"
end "panelhead"
panelbody "id:work1"
content """ In Süderbrarup findet Jahr für Jahr der Maitag statt. Dabei sind wir dies Jahr leider nicht,
aber geht hin, schaut hin, es <b>lohnt</b> sich.
"""
end "panelbody"
panelfooter "id:xxx"
tag ("button","content:Link zur Seite;id:btn_more")
end "panelfooter"
end "panel"
end "col3"

begin ("col3", "class:test")

panel "id:hoop"
panelhead "id:work"
content "Die singende Revolution in Estland"
end "panelhead"
panelbody "id:work1"
content """ Unser nächstes Treffen findet am 25.4 um 18:30 wie immer im Familienzentrum statt. Nach Tausch und Plausch startet unser Filmabend mit
 <b>Die singende Revolution in Estland</b>
"""
end "panelbody"
panelfooter "id=xxx"
tag ("button","content:Mehr;id:btn_singmehr")
tag ("button","content:Hintergrundinfos;id:btn_back")
end "panelfooter"
end "panel"
end "col3"
begin ("col3", "class:test")
panel "hoop"
panelhead "id:work"
content "Noch was"
end "panelhead"
panelbody "id:work1"
content """ Hier kommt der ganze Content
auch über mehrere Zeilen
ich glaube dran, das das <b>funktioniert</b>"""
end "panelbody"
panelfooter "id:xxx"
tag ("button","content:Button1;id:btn_funct")
tag ("button","content:Button2;id:btn_funct2")
end "panelfooter"
end "panel"
end "col3"
begin ("col3", "class:test")
panel "id:hoop"
panelhead "id:work"
content "Noch was"
end "panelhead"
panelbody "id:work1"
content """ Hier kommt der ganze Content
auch über mehrere Zeilen
ich glaube dran, das das <b>funktioniert</b>"""
end "panelbody"
panelfooter "id:xxx"
tag ("button","content:Link1;id:btn_cont")
tag ("button","content:Link2;id:btn_cont4")
end "panelfooter"
end "panel"
end "col3"
end "row"
```
I use this DSL at [Leever-miteenanner.de](http://leever-miteenanner.de)

