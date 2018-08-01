/**
 * Created by larry on 01.08.18.
 */
$(function indexPage() {
// VARIABLES =============================================================
    var TOKEN_KEY = "token";
    var $logoutBtn = $("#logoutBtn").hide();
    var $notLoggedIn = $("#notLoggedIn");
    var $kakaoLoginBtn = $("#kakao-login-btn");

// FUNCTIONS =============================================================
    function getJwtToken() {
        return localStorage.getItem(TOKEN_KEY);
    }

    function setJwtToken(token) {
        localStorage.setItem(TOKEN_KEY, token);
    }

    function removeJwtToken() {
        localStorage.removeItem(TOKEN_KEY);
    }

    function redirect(url) {
        window.location.replace(url);
    }

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

    function doKakaoLogin(authObj) {
        console.log(authObj.access_token);
        var socialLoginDto = {
            provider: 'KAKAO',
            token: authObj.access_token,
        };
        $.ajax({
            url: '/socialLogin',
            type: 'POST',
            data: JSON.stringify(socialLoginDto),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                console.log(response);
                setJwtToken(response.token);
                $notLoggedIn.hide();
                $kakaoLoginBtn.hide();
                $logoutBtn.show();
            }
        });
    }

    function doLogout() {
        removeJwtToken();
        $kakaoLoginBtn.show();
        $notLoggedIn.show();
        $logoutBtn.hide();
    }

// REGISTER EVENT LISTENERS =============================================================
    $(document).ready(function () {
        Kakao.init('ede4e12eaae1e1783add651fd719afdb');
        // 카카오 로그인 버튼을 생성합니다.
        Kakao.Auth.createLoginButton({
            container: '#kakao-login-btn',
            success: function (authObj) {
                doKakaoLogin(authObj);
            },
            fail: function (err) {
                alert(JSON.stringify(err));
            }
        });

        var source = $("#entry-template").html();
        var template = Handlebars.compile(source);
        $.ajax({
            url: "/api/rooms",
            dataType: 'json'
        }).then(function (data) {
            console.log(data);
            var html = template(data);
            $('#content').append(html);
        });
    });

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

    $logoutBtn.click(doLogout);

// INITIAL CALLS =============================================================
    if (getJwtToken()) {
        console.log("can get token");
        console.log($kakaoLoginBtn);
        console.log($notLoggedIn);
        $kakaoLoginBtn.hide();
        $notLoggedIn.hide();
        $logoutBtn.show();
    }
});
