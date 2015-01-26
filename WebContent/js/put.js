/**
 * js file for post.html
 * Please use modern web browser as this file will not attempt to be
 * compatible with older browsers. Use Chrome and open javascript console
 * or Firefox with developer console.
 * 
 * jquery is required
 */
$(document).ready(function() {
	
	var $put_example = $('#put_example')
		, $id = $('#id')
		, $lastname = $('#lastname');
	
	getInventory();
	
	$(document.body).on('click', ':button, .UPDATE_BTN', function(e) {
		//console.log(this);
		var $this = $(this)
			, $tr = $this.closest('tr')
			, id = $tr.find('.CL_ID').text()
			, name = $tr.find('.CL_Name').text()
			, lastname = $tr.find('.CL_LastName').text()
			, age = $tr.find('.CL_Age').text()
			, birthday = $tr.find('.CL_birthday').text();
		
		console.log('ID:'+id);
		console.log('TR:'+$tr);
		console.log('Name:' +name);
		console.log('Lastname:'+lastname);
		console.log($('#lastname').text(lastname));
		console.log('Age:' +age);
		console.log('Birthday:' + birthday);
			
		
		
		//$id.text(id);
		$('#lastname').text(lastname);
		$('#id').text(id);
		$('#name').text(name);
		$('#age').text(age);
		$('#birthday').text(birthday);		
		$('#update_response').text("");
	});
	
	$put_example.submit(function(e) {
		e.preventDefault(); //cancel form submit
		
		console.log($('#lastname').text(lastname));
		
		var obj = $put_example.serializeObject()
			, lastname = $lastname.text()
			, id = $id.text();
		
		console.log('Hey id:'+id);
		console.log('Hey lastname:'+lastname);
			
		
		updateInventory(obj, id, lastname);
	});
});

function updateInventory(obj, id, lastname) {
	
	ajaxObj = {  
			type: "PUT",
			url: "http://localhost:8080/org.jorge.rest/api/v3/inventory/" + id + "/" + lastname,
			data: JSON.stringify(obj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) {
				//console.log(data);
				$('#update_response').text( data[0].MSG );
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
				'<td class="CL_ID">' + param.ID + '</td>' +
				'<td class="CL_Name">' + param.Name + '</td>' +
				'<td class="CL_LastName">' + param.LastName + '</td>' +
				'<td class="CL_Age">' + param.Age + '</td>' +
				'<td class="CL_birthday">' + param.birthday + '</td>' +
				'<td class="CL_persons"> <button class="UPDATE_BTN" value=" ' + param.ID + ' " type="button">Update</button> </td>' +
			'</tr>';
}