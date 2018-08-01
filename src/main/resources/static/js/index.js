/**
 * Created by larry on 01.08.18.
 */
$(function indexPage() {
// VARIABLES =============================================================

// FUNCTIONS =============================================================

    function getToday() {
        var today = new Date();
        var dd = today.getDate();

        var mm = today.getMonth() + 1;
        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd;
        }

        if (mm < 10) {
            mm = '0' + mm;
        }
        return yyyy + '-' + mm + '-' + dd;
    }

// REGISTER EVENT LISTENERS =============================================================

    $(document).on("click", "#reservation-state", function (e) {
        console.log(this)
        e.preventDefault();
        var today = getToday();
        var roomId = $(this).attr("value");
        var url = '/reservations/' + today + '/rooms/' + roomId;
        console.log(url);
        redirect(url);
    })

    $(document).on("click", "#show-reservation", function () {
        $('#content').load("../templates/reservation/show.html");
    });

// INITIAL CALLS =============================================================

});
