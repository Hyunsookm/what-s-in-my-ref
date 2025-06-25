function showTab(tabId) {
    var tabs = document.querySelectorAll('.tab');
    var contents = document.querySelectorAll('.tab-content');

    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });

    contents.forEach(function(content) {
        content.classList.remove('active');
    });

    document.getElementById(tabId).classList.add('active');
    document.querySelector('.tab[onclick="showTab(\'' + tabId + '\')"]').classList.add('active');
}

function idCheck(){
    var form=document.getElementById("find-id-form");

    let emailError=document.getElementById("emailError");

    if(!form.memberEmail.value){
        emailError.style.display="block";
        emailError.style.visibility="visible";
        form.memberEmail.focus();
        return false;
    }else{emailError.style.display="none"; emailError.style.visibility="hidden";}
    form.submit();
}


function pwdCheck(){
    var form=document.getElementById("find-pwd-form");

    let idError=document.getElementById("idError"),
        emailError=document.getElementById("emailError2"),
        error=false;

    if(!form.memberId.value){
        idError.style.display="block";
        idError.style.visibility="visible";
        form.memberId.focus();
        error=true;
    }else{idError.style.display="none"; idError.style.visibility="hidden";}

    if(!form.memberEmail.value){
        emailError.style.display="block";
        emailError.style.visibility="visible";
        form.memberEmail.focus();
        error=true;
    }else{emailError.style.display="none"; emailError.style.visibility="hidden";}

    if(error){
        return false;
    }

    form.submit();
}