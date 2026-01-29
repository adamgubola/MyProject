let currentDate= new Date();
document.getElementById("time").innerHTML="A mai dátum: "+ currentDate.toLocaleDateString("hu-HN");



function myClock(){
 		let currentTime= new Date();
 		document.getElementById("date").innerHTML="A pontosidő: "+ currentTime.toLocaleTimeString("hu-HN");
 	}
 	
 	setInterval(myClock,1000);	