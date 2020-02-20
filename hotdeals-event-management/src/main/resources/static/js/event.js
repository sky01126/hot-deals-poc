// Get the modal

var eventSuccess = false;

var modal = document.getElementById("myModal");

var eventMessage = document.getElementById("id-event-message");
var eventMessageList = [
	"정상적으로 등록되었습니다.",	// 200
	"이벤트 준비중입니다. 잠시 후 이용해 주세요!!",	// 511
	"이벤트가 종료되었습니다.",	// 512
	"이벤트 중복 오류입니다. !!",  // 513
	"시스템 오류입니다. 잠시 후 이용해 주세요!!",
	""										// 미정의
	];
var eventMessageNo = 1;
var eventStatus = 0;	// 0: 준비중 , 1: 진행중	, 2: 종료

var eventImage = document.getElementById("id-event-image");
var eventImageNameList = [
	"/static/images/hotdeals_이벤트.png",
	"/static/images/hotdeals_선착순종료.png",
	"/static/images/hotdeals_이벤트종료.png"
	];
var eventId = document.getElementById("id-event-id");

var eventType = 1;

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

window.addEventListener( "load", function () {
    function sendData2 () {

		var str = '{ "result_code": 200, "result_msg": "SUCCESS",  "data": {    "event_id": "2020011301",    "phone_num": "01012345678",    "name": "홍길동"  }}';
		var obj = JSON.parse(str);
      modal.style.display = "none";
	  alert(obj.data.event_id);
      btn.style.display = "none";
      resultOk.style.display = "block";

	  //eventImage.src = "images/penha.jpg";
	  eventSuccess = true;
    }

	function sendData() {

	    const XHR = new XMLHttpRequest();

	    // Bind the FormData object and the form element
	    const FD = new FormData( form );

	    // Define what happens on successful data submission
	    XHR.addEventListener( "load", function(event) {

			if (XHR.readyState == XMLHttpRequest.DONE) {
				//console(XHR.status);
				if(XHR.status == 200 || XHR.status == 201) {
					alert(event.target.responseText);
					/*alert("응모에 성공했습니다 !!");*/

					var json = event.target.responseText;
					var obj = JSON.parse(json);

					switch(obj.result_code) {
						case 200:
						case 201:
							alert(obj.result_msg);
							eventMessageNo = 0;
							showEventButton(false);
							eventStatus = 1;
							if (obj.data.close_yn == 'undefind' || obj.data.close_yn == false) { // 선착순 마감이 아닌 경우
								eventImage.src = eventImageNameList[0]; // "/static/images/hotdeals_이벤트.png";
							} else {
								eventImage.src = eventImageNameList[1]; //"/static/images/hotdeals_선착순종료.png";
							}
							break;
						case 511:		// 이벤트 준비중입니다.
							alert(obj.result_msg);
							eventMessageNo = 1;
							showEventButton(false);
							eventStatus = 0;
							eventImage.src = eventImageNameList[0]; // "/static/images/hotdeals_이벤트.png";
							break;
						case 512:		// 이벤트가 종료되었습니다.
							alert(obj.result_msg);
							eventMessageNo = 2;
							showEventButton(false);
							eventStatus = 1;
							eventImage.src = eventImageNameList[2]; // "/static/images/hotdeals_이벤트종료.png";
						case 513:		// 이벤트 중복 오류입니다.
							alert(obj.result_msg);
							eventMessageNo = 3;
							showEventButton(false);
							eventStatus = 1;
						default :
							alert(obj.result_msg);
							eventMessageNo = 5;
							eventMessageList[eventMessageNo] = obj.result_msg;
							showEventButton(false);
					//if (obj.result_code
					//showEventButton(1);
					}
				} else {
					alert(XHR.status + " ====== " + event.target.responseText);
					eventMessageNo = 5;
					eventMessageList[eventMessageNo] = obj.result_msg;
					showEventButton(false);
				}

			} else {
				alert("Oops, Unknown Error !!");
				eventMessageNo = 4;
				showEventButton(false);
				eventStatus = 0;
			}
			modal.style.display = "none";
	    });
/*
	    // Define what happens in case of error
	    XHR.addEventListener( "error", function( event ) {
	      alert( 'Oops! Something went wrong.' );
	    });
*/
	    // Set up our request


	    //var url = "http://hotdeals-event-dummy-api.169.56.115.147.nip.io/api/v1/event/type/" + String(eventType);
	    var url = "http://hotdeals-event.169.56.115.147.nip.io/api/v1/event/type/" + String(eventType);
	    XHR.open( "POST", url); // "http://localhost:8080/cassandra/save" );
	    XHR.setRequestHeader("Accept", "application/json");
	    //XHR.setRequestHeader("Content-Type", "application/json");

	    // The data sent is what the user provided in the form
	    XHR.send( FD );
	  }

	  // Access the form element...
	  let form = document.getElementById( "myForm" );

	  // ...and take over its submit event.
	  form.addEventListener( "submit", function ( event ) {

		  if (window.eventSuccess != true) {
			event.preventDefault();
			sendData();
		}
	  });
});

window.onload  = function() {
	//showEventButton(false);
  setTimeout("CheckEventStatus()", 1000);
}


function showEventButton(flag) {
	/*
  resultOk.style.display = "none";
  resultFail.style.display = "none";
  eventWait.style.display = "none";
  btn.style.display = "block";
  */
	if (flag) {
		eventMessage.style.display = "none";
		btn.style.display = "block";
	} else {
		btn.style.display = "none";
		eventMessage.innerHTML = "<p>" + eventMessageList[eventMessageNo] + "</p>";
		eventMessage.style.display = "block";
		//eventMessage.innerHTML = "<p>" + eventMessageList[eventMessageNo] + "</p>";

	}

}

function CheckEventStatus() {
	  function sendData3() {

	    const XHR = new XMLHttpRequest();

	    // Bind the FormData object and the form element
//	    const FD = new FormData( form );

	    // Define what happens on successful data submission
	    XHR.addEventListener( "load", function(event) {

			if (XHR.readyState == XMLHttpRequest.DONE) {
				//console(XHR.status);
				if(XHR.status == 200) {
					alert(event.target.responseText);
					/*alert("응모에 성공했습니다 !!");*/

					var json = event.target.responseText;
					var obj = JSON.parse(json);

					// obj.data.close_yn = true;

					switch(obj.result_code) {
						case 200:
							eventMessageNo = 0;
							showEventButton(true);
							eventId.value = obj.data.event_id;
							eventType = obj.data.event_type;
							eventStatus = 1;
							if (obj.data.close_yn == 'undefind' || obj.data.close_yn == false) { // 선착순 마감이 아닌 경우
								eventImage.src = eventImageNameList[0]; // "/static/images/hotdeals_이벤트.png";
							} else {
								eventImage.src = eventImageNameList[1]; // "/static/images/hotdeals_선착순종료.png";
							}
							break;
						case 511:			// 이벤트 준비중입니다.
							eventMessageNo = 1;
							showEventButton(false);
							eventStatus = 0;
							setTimeout("CheckEventStatus()", 60000);
							eventImage.src = eventImageNameList[0]; // "/static/images/hotdeals_이벤트.png";
							break;
						case 512:			// 이벤트가 종료되었습니다.
							eventMessageNo = 2;
							showEventButton(false);
							eventStatus = 2;
							eventImage.src = eventImageNameList[2]; // "/static/images/hotdeals_이벤트종료.png";
						default :
							eventMessageNo = 5;
							eventMessageList[eventMessageNo] = obj.result_msg;
							showEventButton(false);
					}
				}

			} else {

				eventMessageNo = 4;
				showEventButton(false);
				eventStatus = 0;
				setTimeout("CheckEventStatus()", 60000);
			}
	    });
/*
	    // Define what happens in case of error
	    XHR.addEventListener( "error", function( event ) {
	      alert( 'Oops! Something went wrong.' );
	    });
*/
	    // Set up our request
	    XHR.open( "GET", "http://hotdeals-event.169.56.115.147.nip.io/api/v1/event/init");
	    		//"http://hotdeals-event-dummy-api.169.56.115.147.nip.io/api/v1/event/init" );
	    XHR.setRequestHeader("Accept", "application/json");
	    // The data sent is what the user provided in the form
	    XHR.send(  );
	  }
	if(eventStatus == 0) {
		sendData3();
	}
}
