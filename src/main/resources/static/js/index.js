$(document).ready(function () {
    $.ajax({
        url: "/api/users/1"
    }).then(function (data) {
        $('.user-id').append(data.id);
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
