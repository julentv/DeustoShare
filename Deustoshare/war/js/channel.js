var connected=false;
function channelBegin(){
	channel = new goog.appengine.Channel('{{ token }}');
    socket = channel.open();
    socket.onopen = onOpened;
    socket.onmessage = onMessage();
    socket.onerror = onError();
    socket.onclose = onClose;
}
onOpened=function(){
	connected=true;
	
}
onMessage=function(msg){
	blinkingMenu();
}
onError=function(err){
	alert("Connection Error!");
}
onClose=function(){
	//channelBegin();
	connected=false;
}

function blinkingMenu(){
	document.getElementById("receivedRequestSpan").innerHTML="";
	showText("#receivedRequestSpan", "New Request ", 0, 300, 0);
	//document.getElementById("receivedRequestSpan").innerHTML="Received requests";
	
}
var showText = function (target, message, index, interval, vuelta) {   
	  if (index < message.length) {
	    $(target).append(message[index++]);
	    setTimeout(function () { showText(target, message, index, interval, vuelta); }, interval);
	  }else{
		  if(vuelta<3){
			  document.getElementById("receivedRequestSpan").innerHTML="";
			  showText(target, message, 0, interval, vuelta+1);  
		  }else{
			  document.getElementById("receivedRequestSpan").innerHTML="Received requests";  
		  }
		  	
	  }
	  
}
