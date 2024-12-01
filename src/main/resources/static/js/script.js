

 const savedMoneyElements = document.querySelectorAll("[id^=savedMoneyIn_]");
 const savedMoneyPopUp = document.getElementById("savedMoneyPopUp");
 
 savedMoneyElements.forEach((savedMoney) => {
	 const index = savedMoney.id.split('_').pop(); 
	 const price = document.getElementById(`priceIn_${index}`); 
 
	 if (!price) {
		 console.error(`A price elem nem található az index alapján: ${index}`);
		 return;
	 }
 
	 savedMoney.addEventListener('mouseenter', function(_event) {
		 let savedMoneyValue = parseFloat(savedMoney.textContent);
		 let priceValue = parseFloat(price.textContent);
		 let result = 0;
 
		 if (!isNaN(savedMoneyValue) && !isNaN(priceValue)) {
			 result = priceValue - savedMoneyValue;
			 savedMoneyPopUp.innerHTML = result + " forintra van még szükséged!";
		 } else {
			 savedMoneyPopUp.innerHTML = "Valami hiba csúszott a számításba";
		 }
 
		 savedMoneyPopUp.style.display = 'block';
		 savedMoneyPopUp.style.left = (_event.pageX + 10) + 'px';
		 savedMoneyPopUp.style.top = (_event.pageY + 10) + 'px';
	 });
 
	 savedMoney.addEventListener('mousemove', function(_event) {
		 savedMoneyPopUp.style.left = (_event.pageX + 10) + 'px';
		 savedMoneyPopUp.style.top = (_event.pageY + 10) + 'px';
	 });
 
	 savedMoney.addEventListener('mouseout', function(_event) {
		 savedMoneyPopUp.style.display = 'none';
	 });
 });


 function displayUpdateInput(){

 	const displayedWishes = document.querySelectorAll('.columns');


	displayedWishes.forEach(element =>{
		element.addEventListener('click', (_event)=>{
			_event.target.style.display='none';
			
   			const inputElement = _event.target.closest('td').querySelector('.columns-update');
            
      
            if (inputElement) {
                inputElement.style.display = 'block';
            }
		
            const row = _event.target.closest('tr');
            const updateButton = row.querySelector('.columns-button-update');
            const deleteButton = row.querySelector('.columns-button-del');
            
            if (updateButton) {
                updateButton.style.display = 'block';
            }

            if (deleteButton) {
                deleteButton.style.display = 'none';
            }

		});
	});
}
document.addEventListener('DOMContentLoaded', displayUpdateInput);



function alertMessageHide(){
	const messageElement= document.getElementById('alert-message');


	if(messageElement ){
		console.log('Van message element')

		setTimeout(()=>{
			messageElement.style.display='none';
		},5000);
	}
}

function alertErrorHide(){
	const errorElement= document.getElementById('alert-error');


	if(errorElement ){
		console.log('Van message element')

		setTimeout(()=>{
			errorElement.style.display='none';
		},5000);
	}
}
document.addEventListener('DOMContentLoaded', alertMessageHide);
document.addEventListener('DOMContentLoaded', alertErrorHide);

document.addEventListener("DOMContentLoaded", function () {
    const logoutLink = document.getElementById("logoutLink");
    const logoutForm = document.getElementById("frmlogout");

    if (logoutLink && logoutForm) { 
        logoutLink.addEventListener("click", function (event) {
            event.preventDefault();
            logoutForm.submit();
        });
    } else {
        console.error("Az egyik elem hiányzik: logoutLink vagy frmlogout.");
    }
});

