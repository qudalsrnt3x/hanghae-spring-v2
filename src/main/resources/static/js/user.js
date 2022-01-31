let index = {
    init: function (){
        $('#btn-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });
    },

    save: function (){
        //alert('call save');

        let data = {
            username: $('#username').val(),
            password: $('#password').val(),
            email: $('#email').val()
        };

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
            if (response['status'] === 201) {
                alert("회원가입이 완료 되었습니다.")
                location.href = '/auth/login';
            }
            console.log(response);
            //location.href = '/auth/loginForm';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }

}

index.init();