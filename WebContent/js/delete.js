/**
 * js file for post.html
 * Please use modern web browser as this file will not attempt to be
 * compatible with older browsers. Use Chrome and open javascript console
 * or Firefox with developer console.
 * 
 * jquery is required
 */
$(document).ready(function() {
	
	getInventory();
	
	$(document.body).on('click', ':button, .DELETE_BTN', function(e) {
		//console.log(this);
		var $this = $(this)
			, $tr = $this.closest('tr')
			, myID = $tr.find('.CL_id').text()
			,obj = {myID : myID};
		
		console.log('ID:'+myID);
		
		deleteInventory(obj, myID, myID);
	});
});

function deleteInventory(obj, id, id) {
	
	ajaxObj = {  
			type: "DELETE",
			url: "http://localhost:8080/org.jorge.rest/api/v3/inventory/" + id + "/" + id,
			data: JSON.stringify(obj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) {
				//console.log(data);
				$('#delete_response').text( data[0].MSG );
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
				getInventory();
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function getInventory() {
	
	var d = new Date()
		, n = d.getTime();
	
	ajaxObj = {  
			type: "GET",
			url: "http://localhost:8080/org.jorge.rest/api/v1/inventory", 
			data: "ts="+n,
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) { 
				//console.log(data);
				var html_string = "";
				
				$.each(data, function(index1, val1) {
					console.log(val1);
					html_string = html_string + templateGetInventory(val1);
				});
				
				$('#get_inventory').html("<table border='1'>" + html_string + "</table>");
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function templateGetInventory(param) {
	return '<tr>' +
				'<td class="CL_id">' + param.ID + '</td>' +
				'<td class="CL_name">' + param.Name + '</td>' +
				'<td class="CL_lastname">' + param.LastName + '</td>' +
				'<td class="CL_age">' + param.Age + '</td>' +
				'<td class="CL_birthday">' + param.birthday + '</td>' +
				'<td class="CL_PC_PARTS_BTN"> <button class="DELETE_BTN" value=" ' + param.id + ' " type="button">Delete</button> </td>' +
			'</tr>';
}