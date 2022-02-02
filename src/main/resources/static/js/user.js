let joinCheck = false;
let pwCheck = false;
let pw2Check = false;

let index = {
    init: function (){
        $('#btn-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });
    },

    checkId: function () {

        let data = {
            username : $('#username').val()
        };

        $.ajax({
            type: 'POST',
            url: "/auth/idCheck",
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function (response){
            if (response !== 1 && is_nickname(data.username)) {
                $('.id_ok').css("display","inline-block");
                $('.id_already').css("display", "none");
                $('.id_check').css("display", "none");
                $('#username').css("borderColor","lightgreen")

                joinCheck = true;
            } else if (response === 1) {
                $('.id_already').css("display", "inline-block");
                $('.id_ok').css("display", "none");
                $('.id_check').css("display", "none");
                $('#username').css("borderColor","red")

                joinCheck = false;
            } else if (!is_nickname(data.username)) {
                $('.id_check').css("display", "inline-block");
                $('.id_ok').css("display", "none");
                $('.id_already').css("display", "none");
                $('#username').css("borderColor","silver")
                joinCheck = false;
            }
        }).fail(function (error){
            alert(error)
        });
    },

    checkPw: function () {

        let username = $('#username').val();
        let password = $('#password').val();

        if(isMatched(username, password)) {
            $('.pw_check').css("display", "none");
            $('.pw_ok').css("display", "none")
            $('.pw_duplicate').css("display", "inline-block")
            $('#password').css("borderColor","red")
            pwCheck = false;
        } else {
            if (is_password(password)) {
                $('.pw_check').css("display", "none");
                $('.pw_ok').css("display", "inline-block")
                $('.pw_duplicate').css("display", "none")
                $('#password').css("borderColor","lightgreen")

                pwCheck = true;
            } else {
                $('.pw_ok').css("display", "none")
                $('.pw_check').css("display", "inline-block")
                $('.pw_duplicate').css("display", "none")
                $('#password').css("borderColor", "silver")
                pwCheck = false;
            }
        }


    },

    checkPw2: function () {
        let checkPw = $('#passwordCheck').val();
        let password = $('#password').val();

        if (password === checkPw) {
            $('.pwCheck_ok').css("display", "inline-block")
            $('.pwCheck_check').css("display", "none")
            $('#passwordCheck').css("borderColor","lightgreen")

            pw2Check = true;
        } else {
            $('.pwCheck_ok').css("display", "none")
            $('.pwCheck_check').css("display", "inline-block")
            $('#passwordCheck').css("borderColor","silver")
            pw2Check = false;
        }
    },

    save: function (){
        //alert('call save');

        let data = {
            username: $('#username').val(),
            password: $('#password').val(),
            passwordCheck: $('#passwordCheck').val(),
            email: $('#email').val()
        };

        if (joinCheck === false || pwCheck === false) {
            alert('아이디 및 패스워드를 확인해주세요.');
            $('#username').focus();
            return;
        }

        // ajax 호출시 default가 비동기 호출
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 Insert 요청
        // ajax ㄱㅏ 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바스크립트 오브젝트로 변환해준다.
        $.ajax({
            // 회원가입 수행요청
            type: 'POST',
            url : "/auth/signup",
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function (response){
            if (response['success'] === true) {
                alert(response['message']);
                location.href = '/auth/login';

            }
        }).fail(function (error){
            alert(error['responseJSON']['message']);
        });
    }

}

index.init();

function is_nickname(asValue) {
    let regExp = /^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{3,10}$/;
    return regExp.test(asValue);
}

function is_password(asValue) {
    let regExp = /^(?=.*\d)[0-9a-zA-Z]{4,20}$/;
    return regExp.test(asValue);
}

function isMatched(id, pw) {
    return pw.match(id) !== null;
}