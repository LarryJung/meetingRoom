$(document).ready(function () {
    $.ajax({
        url: "/api/users/1"
    }).then(function (data) {
        $('.user-id').append(data.userId);
        $('.user-email').append(data.email);
    });
});

$(document).ready(function () {
    var source = $("#entry-template").html();
    var template = Handlebars.compile(source);
    $.ajax({
        url: "/api/rooms",
        dataType : 'json'
    }).then(function (data) {
        console.log(data);
        var html = template(data);
        $('body').append(html);
    });
});

$(document).on("click", "#show-reservation", function(){
    // var url = "../templates/reservation/show.html";
    // console.log(url);
    $('#content').load("../templates/reservation/show.html");
    // alert($(this).attr("value"));
    // redirect(url);
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
