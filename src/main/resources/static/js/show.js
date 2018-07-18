// $(document).ready(function () {
//     $('#room-id').append(101);
//     $('#occupancy').append(5);
// });

$(document).ready(function () {
    console.log('야이야이이이')
    var source = $("#entry-template").html();
    console.log(source);
    var template = Handlebars.compile(source);
    console.log(template);
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
        $('body').append(html);
    });
});

$('#btnSave').click(function (e) {
    e.preventDefault();
    var reservation = {
        reservedDate: $('#reserved-date').val(),
        startTime: $('#start-time').val(),
        endTime: $('#end-time').val(),
        numberOfAttendee: $('#number-of-attendee').val()
    };
    console.log(reservation);
    $.ajax({
        url: $("#reservation-form").attr("action"),
        method: 'POST',
        data: JSON.stringify(reservation),
        contentType: "application/json; charset=utf-8",
        success: function () {
            redirect("/");
        }
    });
});

// $(document).ready(function() {
//     $('#show-reservation').click(function(){
//         var url = "../templates/reservation/show";
//         console.log(url);
//         alert($(this).attr("value"));
//         redirect(url);
//     });
// });

function redirect(url) {
    window.location.replace(url);
}
