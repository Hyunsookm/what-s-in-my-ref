function reWritePw_check() {
    var form=document.getElementById("reWritePw-form");

    let pwdError=document.getElementById("pwdError"),
        repwdError=document.getElementById("repwdError"),
        
        pwdCheck=/^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{8,20}$/,
        error=false;


    if(!pwdCheck.test(form.password.value)){  //비밀번호 형식이 맞지 않을 때
        pwdError.style.display="block";
        pwdError.style.visibility="visible";
        error=true;
    }else{pwdError.style.display="none"; pwdError.style.visibility="hidden";}
   
    if(form.password.value!=form.repassword.value){ //재확인 비밀번호가 다를 때
        repwdError.style.display="block";
        repwdError.style.visibility="visible";
        error=true;
    }else{repwdError.style.display="none"; repwdError.style.visibility="hidden";}
    
    if(error==true){    //에러 존재시 submit x
        return false;
    }

    form.submit();
    return true;
}


function enterPushButton(event){
    if(event.keyCode===13){
      document.getElementById("reWritePw-btn").click();
    }
  }
  
document.getElementById("reWritePw-form").addEventListener("keypress",enterPushButton);