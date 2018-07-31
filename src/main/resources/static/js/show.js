// $(document).ready(function () {
//     $('#room-id').append(101);
//     $('#occupancy').append(5);
// });

$(document).ready(function () {
    var source = $("#entry-template").html();
    var template = Handlebars.compile(source);
    var date = $("#day").text().trim();
    var roomId = $("#room-id").text().trim();
    var url = "/api/reservations/"+date+"/rooms/"+roomId;
    console.log("url : "+ url);
    $.ajax({
        url: url,
        dataType: 'json'
    }).then(function (data) {
        console.log(data);
        var html = template(data);
        console.log(html);
        $('#reservation-table tbody').append(html);
    });
});

$('#btnSave').click(function (e) {
    e.preventDefault();
    var reservation = {
        reservedDate: $('#reserved-date').val(),
        startTime: $('#start-time').val(),
        endTime: $('#end-time').val(),
        numberOfAttendee: $('#number-of-attendee').val(),
    };
    var token = localStorage.getItem('token');
    //Setup headers here and than call ajax
    $.ajaxSetup({
        headers: {'Authorization': 'Bearer '+ token}
    });
    console.log(reservation);
    $.ajax({
        url: $("#reservation-form").attr("action"),
        type: 'POST',
        data: JSON.stringify(reservation),
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log(data);
            myCreateFunction(data);
            // redirect("/");
        }
    });
});

function myCreateFunction(data) {
    var source = $("#entry-template").html();
    var template = Handlebars.compile(source);
    var html = template([data]);
    console.log(html);
    $("#reservation-table").last().append(html);
}

function redirect(url) {
    window.location.replace(url);
}
