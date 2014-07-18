var globalWord;
var failedChances;
var globalArrayIndex;
var globalImageArray = ["img/hangImage0.gif","img/hangImage1.gif","img/hangImage2.gif","img/hangImage3.gif","img/hangImage4.gif",
                        "img/hangImage5.gif","img/hangImage6.gif"];


/*
 * Returns a new XMLHttpRequest object, or false if this browser
 * doesn't support it
 */
function newXMLHttpRequest() {
  var xmlreq = false;

  if (window.XMLHttpRequest) {
    xmlreq = new XMLHttpRequest();

  } else if (window.ActiveXObject) {

    try {
      xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

    } catch (e1) {
      try {
        xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (e2) {
        // Unable to create an XMLHttpRequest using ActiveX
      }
    }
  }
  return xmlreq;
}

function getReadyStateHandler(req, responseXmlHandler) {
	  return function () {
	    // If the request's status is "complete"
	    if (req.readyState == 4) {
	      if (req.status == 200) {
	        responseXmlHandler(req.responseText);
	      } else {
	        // An HTTP problem has occurred
	        alert("HTTP error: "+req.status);
	      }
	    }
	  };
}

function loadWord(category) {
	  // Obtain an XMLHttpRequest instance
	  var req = newXMLHttpRequest();

	  var handlerFunction = getReadyStateHandler(req, updateWord);
	  
	  req.onreadystatechange = handlerFunction;
	  req.open("POST", "game.do", true);
	  req.setRequestHeader("Content-Type", 
	                       "application/x-www-form-urlencoded");
	  req.send("action=loadWord&value="+category);
}

function updateWord(word) {
	 document.getElementById("hangmanImage").style.visibility = "visible";
	 globalWord = word;
	 failedChances = 0;
	 globalArrayIndex = 0;
	 loadWordTable(word);
	 loadLettersTable();
	 updateImage(globalArrayIndex);
}

function loadWordTable(word){
	var table = "<tr>";
	for(var index=0; index<word.split('').length; index++){
		table += "<td ><a id='wordLetter"+index+"' class='wordLetter'>_</a></td>";
	}
	table += "</tr>";
	document.getElementById("wordTable").innerHTML = table;
}

function loadLettersTable(){
	var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split('');

	var table = "<tr>";
	for(var index=0; index<=12; index++){
		table += "<td ><a id='"+alphabet[index]+"' onClick='javascript:verifyLetter(this.id);' class='letterActive'>"+alphabet[index]+"</a></td>";
	}
	table += "</tr>";
	table += "<tr>";
	for(var index=13; index<=25; index++){
		table += "<td ><a id='"+alphabet[index]+"' onClick='javascript:verifyLetter(this.id);' class='letterActive'>"+alphabet[index]+"</a></td>";
	}
	table += "</tr>";
	document.getElementById("lettersTable").innerHTML = table;
}

function updateImage(index){
	document.getElementById("hangmanImage").src = globalImageArray[index];
}

function verifyLetter(letter) {
	if(failedChances<globalWord.split('').length){
		if (globalArrayIndex < 6){
			globalArrayIndex+= updateGame(letter);
			if(globalArrayIndex == 6){
				alert("Game Over!");
			}
		} else{
			alert("Try another category!");
		}
	}else{
		alert("You Win!");
	}
}

function updateGame(letter) {
	var find = false;
	var wordSplit = globalWord.split('');
	for(var index=0; index<wordSplit.length; index++){
		if(letter == wordSplit[index]){
			document.getElementById("wordLetter"+index).innerHTML = wordSplit[index];
			failedChances+=1;
			find = true;
		}
	}
	document.getElementById(letter).className = "letterInactive";
	document.getElementById(letter).onclick = "";
	if(find){
		return 0;
	}else{		
		updateImage(globalArrayIndex+1);
		return 1;
	}	
}