var recent_longinId = "";

/*---------------------------------------------------------------
유효성 검사
----------------------------------------------------------------*/
function loginChk() {
  //console.log("loginChk 함수 호출됨");
  var form = document.getElementById("login-form");
  //아이디 입력 없음.
  if (!form.loginId.value) {
    alert("아이디를 입력해 주십시오.");
    form.loginId.focus();
    return;
  }
  //비밀번호 입력 없음.
  else if (!form.password.value) {
    alert("비밀번호를 입력해 주십시오.");
    form.password.focus();
    return;
  }
  //계정 로그인 시도 제한를 위한 아이디 저장
  recent_longinId = form.loginId.value;
  form.submit();
}

/*---------------------------------------------------------------
로그인 실패 메시지
----------------------------------------------------------------*/
var error_code = "[[${error_code}]]";
if (error_code != "") {
  if (error_code == "0") {
    //에러코드 0 : 비밀번호 틀림
    if (!localStorage.getItem(recent_longinId)) {
      let zero = 0;
      localStorage.setItem(recent_longinId, zero.toString());
    } else {
      let count = localStorage.getItem(recent_longinId);
      localStorage.setItem(recent_longinId, (count + 1).toString());
    }
  }
}

/*---------------------------------------------------------------
아이디/비밀번호 찾기 창 띄우기
----------------------------------------------------------------*/
function openInNewWindow(event, url) {
  event.preventDefault();
  var width = 800;
  var height = 600;
  var left = (screen.width - width) / 2;
  var top = (screen.height - height) / 2;

  window.open(
    url,
    "newwindow",
    "width=" + width + ", height=" + height + ", left=" + left + ", top=" + top
  );
}

/*---------------------------------------------------------------
엔터 키로 로그인
----------------------------------------------------------------*/
function enterPushButton(event){
  if(event.keyCode===13){
    document.getElementById("login-btn").click();
  }
}

document.getElementById("login-form").addEventListener("keypress",enterPushButton);