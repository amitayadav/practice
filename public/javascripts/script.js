
var signupmodal = document.getElementById('id01');
window.onclick = function(event) {
    if (event.target == signupmodal) {
        modal.style.display = "none";
    }
}

var loginmodal = document.getElementById('id02');
window.onclick = function(event) {
    if (event.target == loginmodal) {
        modal.style.display = "none";
    }
}


function formValidate()
{
 return (nameValidation(document.customForm.firstName.value) &&
         nameValidation(document.customForm.middleName.value) &&
         nameValidation(document.customFormm.lastName.value) &&
         passwordValidate()
         );
}

function nameValidate(name){
  console.log(name);
  var NameErr = document.getElementById('firstName_error');
  if( /^[a-zA-Z]*$/.test(name) )
  {
    NameErr.innerHTML = "";
    return true;
  } else {
    NameErr.innerHTML = "   *Name should not contain numbers and special characters!";
    document.myForm.firstName.focus() ;
    return false;
  }
}


function passwordValidate(){
 var confirmPasswordError=document.getElementById('confirmPassword');
 if( document.myForm.password.value != document.myForm.confirmPassword.value )
 {
   confirmPasswordError.innerHTML=" * Passwords do not match. Please check!";
   return false;
 }
 else{
  confirmPasswordError.innerHTML="";
  return true;
 }
}



