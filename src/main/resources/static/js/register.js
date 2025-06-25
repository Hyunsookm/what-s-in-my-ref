//id: 숫자와 영어의 조합
function form_check() {
  var form = document.getElementById("register-form");

  let idError = document.getElementById("idError"),
    pwdError = document.getElementById("pwdError"),
    repwdError = document.getElementById("repwdError"),
    nameError = document.getElementById("nameError"),
    emailError = document.getElementById("emailError"),
    full_email = form.email_id.value + "@" + form.email_add.value,
    idCheck = /^[A-Za-z][A-Za-z0-9]{2,14}$/,
    pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{8,20}$/,
    emailCheck =
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/,
    nameCheck = /^[0-9a-zA-Z가-힣]{3,10}$/,
    error = false;

  if (!idCheck.test(form.loginId.value)) {
    //아이디 형식이 다를 때(영어로 시작하는 영어+숫자 3~15자리)
    idError.style.display = "block";
    idError.style.visibility = "visible";
    error = true;
  } else {
    idError.style.display = "none";
    idError.style.visibility = "hidden";
  }

  if (!pwdCheck.test(form.password.value)) {
    //비밀번호 형식이 맞지 않을 때
    pwdError.style.display = "block";
    pwdError.style.visibility = "visible";
    error = true;
  } else {
    pwdError.style.display = "none";
    pwdError.style.visibility = "hidden";
  }

  if (form.password.value != form.repassword.value) {
    //재확인 비밀번호가 다를 때
    repwdError.style.display = "block";
    repwdError.style.visibility = "visible";
    error = true;
  } else {
    repwdError.style.display = "none";
    repwdError.style.visibility = "hidden";
  }

  if (!nameCheck.test(form.name.value)) {
    //닉네임 입력이 없을 때
    nameError.style.display = "block";
    nameError.style.visibility = "visible";
    error = true;
  } else {
    nameError.style.display = "none";
    nameError.style.visibility = "hidden";
  }

  if (!emailCheck.test(full_email)) {
    //이메일 형식이 다를 때
    emailError.style.display = "block";
    emailError.style.visibility = "visible";
    error = true;
  } else {
    emailError.style.display = "none";
    emailError.style.visibility = "hidden";
  }

  if (error == true) {
    //에러 존재시 submit x
    return false;
  }

  document.getElementById("email").value = full_email;
  form.submit();
  return true;
}

function change_email() {
  var email_add = document.getElementById("email_add");
  var email_sel = document.getElementById("email_sel");

  var idx = email_sel.options.selectedIndex;
  var val = email_sel.options[idx].value;

  email_add.value = val;
}
