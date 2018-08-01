/**
 * Created by larry on 01.08.18.
 */

// VARIABLES =============================================================


// FUNCTIONS =============================================================
function getJwtToken() {
    var TOKEN_KEY = "token";
    return localStorage.getItem(TOKEN_KEY);
}

function setJwtToken(token) {
    var TOKEN_KEY = "token";
    localStorage.setItem(TOKEN_KEY, token);
}

function removeJwtToken() {
    console.log("remove token")
    var TOKEN_KEY = "token";
    localStorage.removeItem(TOKEN_KEY);
}

function redirect(url) {
    window.location.replace(url);
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
            $("#kakao-login-btn").hide();
            $("#logoutBtn").show();
            $("#notLoggedIn").hide();
        }
    });
}

function doLogout() {
    console.log("do logout")
    removeJwtToken();
    $("#kakao-login-btn").show();
    $("#notLoggedIn").show();
    $("#logoutBtn").hide();
}

// REGISTER EVENT LISTENERS =============================================================

// $(document).on('click', $("#logoutBtn"), doLogout());

// INITIAL CALLS =============================================================
$(document).ready(function () {

    if (getJwtToken() != null) {
        $("#kakao-login-btn").hide();
        $("#notLoggedIn").hide();
        $("#logoutBtn").show();
    }

    if (getJwtToken() == null) {
        $("#kakao-login-btn").show();
        $("#notLoggedIn").show();
        $("#logoutBtn").hide();
    }

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
        $('#tableContent').append(html);
    });
});

$('#logoutBtn').click(doLogout);

