$(document).ready(function() {
    if ($('#alertSuccess').text().trim() == "") {
        $('#alertSuccess').hide();
    }

    $('#alertError').hide();
})

// SAVE
$(document).on("click","#btnSave", function(event) {
    // Clear alerts
    $("#alertSuccess").text(""); 
    $("#alertSuccess").hide(); 
    $("#alertError").text(""); 
    $("#alertError").hide();

    // Form validation
    var status = validateItemForm(); 
    if (status != true) 
    { 
        $("#alertError").text(status); 
        $("#alertError").show(); 
        return; 
    } 

    // if hidItemIDSave value is null set as POST else set as PUT
    var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

    // ajax communication
    $.ajax({
        url: "ConnectionAPI",
        type: type,
        data: $("#formItem").serialize(),
        dataType: "text",
        complete: function(response, status) {
            onConnectionSaveComplete(response.responseText, status);
        }
    });
});

// after completing save request
function onConnectionSaveComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully saved");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divItemsGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while saving");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while saving");
        $("#alertError").show();
    } 

    //resetting the form
    $("#hidItemIDSave").val("");
    $("#formItem")[0].reset();
}

// UPDATE
//to identify the update button we didn't use an id we used a class
$(document).on("click", ".btnUpdate", function(event) 
{ 
    //get item id from the data-itemid attribute in update button
    $("#hidItemIDSave").val($(this).data('connectionid')); 
    //get data from <td> element
    $("#connectionID").val($(this).closest("tr").find('td:eq(0)').text()); 
    $("#status").val($(this).closest("tr").find('td:eq(1)').text()); 
    $("#units").val($(this).closest("tr").find('td:eq(2)').text());  
}); 

// DELETE
$(document).on("click",".btnRemove", function(event) {
    // ajax communication
    $.ajax({
        url: "ConnectionAPI",
        type: "DELETE",
        data: "connectionID=" + $(this).data("connectionid"),
        dataType: "text",
        complete: function(response, status) {
            onConDeleteComplete(response.responseText, status);
        }
    });
});

// after completing delete request
function onConDeleteComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully deleted");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divItemsGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while deleting");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while deleting");
        $("#alertError").show();
    } 
}

// VALIDATION
function validateItemForm() { 
    // CODE 
    if ($("#connectionID").val().trim() == "") 
    { 
        return "Insert connectionID."; 
    } 
    
    // NAME 
    if ($("#customerID").val().trim() == "") 
    { 
        return "Insert customer ID."; 
    } 
    
    // PRICE
    if ($("#type").val().trim() == "") 
    { 
        return "Insert connection type."; 
    } 
    
    // PRICE
    if ($("#status").val().trim() == "") 
    { 
        return "Insert connection status."; 
    } 
    
    // is numerical value 
    var id = $("#connectionID").val().trim(); 
    if (!$.isNumeric(id)) 
    { 
        return "Insert a numerical value for connectionID."; 
    } 
    
    
    
    return true; 
} 
 