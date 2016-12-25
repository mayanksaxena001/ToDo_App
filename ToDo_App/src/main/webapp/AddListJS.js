var response;
$(document).ready(function () {
    $('button').click(function () {
        $('#todo').append("<ul id='ar'>" + $("input[name=task]").val() + " <a href='#' class='close' aria-hidden='true'>&times;</a></ul>");
		 var xhttp = new XMLHttpRequest();
		 xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
		var ob=$('#ar');
		var res=JSON.parse(this.responseText);;
        ob[0].id = res.id;
    }
  };
        xhttp.open("POST", "https://evening-meadow-31025.herokuapp.com/api/service/add", true);
         xhttp.setRequestHeader("Content-type", "application/json");
		 var jsonObject={"id":0,"text":$("input[name=task]").val(),"createdDate":Math.floor(Date.now()),"dueDate":$("input[name=due]").val()};
         xhttp.send(JSON.stringify(jsonObject));
    });
    $("body").on('click', '#todo a', function () {
		var ul= $(this).closest("ul");
		var id=ul[0].id;
        $(this).closest("ul").remove();
		 var xhttp = new XMLHttpRequest();
		 var url="https://evening-meadow-31025.herokuapp.com/api/service/delete/"+id;
		 xhttp.open("DELETE", url, true);
		  xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
         xhttp.setRequestHeader("Content-type", "application/json");
		 xhttp.send();
		 
    });
});
