//원래있던 제품
//console.log(originalProductList);
//원래있었던 배송방법
//console.log(originalShipmentList);

let sno = document.getElementById('sno').value;
let realSno = parseInt(sno);
//console.log("sno >> "+realSno);

window.onload = function(){

    originalProductList.forEach(product=>{
        //product <- 하나의 상품객체
        //console.log(product);

            let saleProductArea = document.querySelector('.saleProductArea');
        
            let saleProductBox = document.createElement('div');
            saleProductBox.classList.add('saleProductBox');

            let delBtnWrap = document.createElement('div');
            delBtnWrap.classList.add('productDelBtnWrap');
            let delBtn = document.createElement('button');
            delBtn.classList.add('productDelBtn');
            delBtn.textContent = 'X';
            delBtnWrap.appendChild(delBtn);
            saleProductBox.appendChild(delBtnWrap);
        
            let boxWrapper = document.createElement('div');
            boxWrapper.classList.add('boxWrapper');
        
            let productImgWrapper = document.createElement('div');
            productImgWrapper.classList.add('productImgWrapper');
        
            let productImg = document.createElement('div');
            productImg.id = 'productImg';
            productImg.classList.add('productImg');

            //이미지 추가
            //var imageUrl = `/upload/saleProductImg/${product.spivo.saveDir.replace(/\\/g, '/')}/${product.spivo.uuid}_${product.spivo.fileName}`;

            // let img = document.createElement("img");
            // //img.src = `${imageUrl}`;
            // img.style.width = "130px";
            // img.style.height = "130px";
            // img.style.objectFit = "cover";
            // img.style.borderRadius = "8px";


            // productImg.appendChild(img);
        
            let imageIcon = document.createElement('i');
            imageIcon.classList.add('fa-regular', 'fa-image');
            productImg.appendChild(imageIcon);
            productImgWrapper.appendChild(productImg);
        
            let imgInput = document.createElement('input');
            imgInput.type = 'file';
            imgInput.id = 'productImgFile';
            imgInput.name = 'productImgFile';
            imgInput.style.display = 'none';
            imgInput.classList.add('productImgFile');
            imgInput.setAttribute('onchange', 'showProductImg(event)');
            productImgWrapper.appendChild(imgInput);
        
            boxWrapper.appendChild(productImgWrapper);
        
            let productContent = document.createElement('div');
            productContent.classList.add('productContent');
        
            //상품명
            let productNameDiv = document.createElement('div');
            productNameDiv.classList.add('productNameDiv');
            let nameLabel = document.createElement('div');
            nameLabel.textContent = '상품명';
            let nameInputWrapper = document.createElement('div');
            let nameInput = document.createElement('input');
            nameInput.type = 'text';
            nameInput.classList.add('pNameInput');
            nameInput.value = `${product.spvo.spname}`;

            nameInputWrapper.appendChild(nameInput);
            productNameDiv.appendChild(nameLabel);
            productNameDiv.appendChild(nameInputWrapper);
        
            productContent.appendChild(productNameDiv);
        
            //가격
            let productPriceDiv = document.createElement('div');
            productPriceDiv.classList.add('productPriceDiv');
            let priceLabel = document.createElement('div');
            priceLabel.textContent = '가격';
            let priceInputWrapper = document.createElement('div');
            let priceInput = document.createElement('input');
            priceInput.type = 'text';
            priceInput.classList.add('pPriceInput');
            priceInput.value = `${product.spvo.spprice}`;

            priceInputWrapper.appendChild(priceInput);
            productPriceDiv.appendChild(priceLabel);
            productPriceDiv.appendChild(priceInputWrapper);
        
            productContent.appendChild(productPriceDiv);
        
            //재고
            let productStockDiv = document.createElement('div');
            productStockDiv.classList.add('productStockDiv');
            let stockLabel = document.createElement('div');
            stockLabel.textContent = '재고';
            let stockInputWrapper = document.createElement('div');
            let stockInput = document.createElement('input');
            stockInput.type = 'text';
            stockInput.classList.add('pStockInput');
            stockInput.value =  `${product.spvo.stock}`;

            stockInputWrapper.appendChild(stockInput);
            productStockDiv.appendChild(stockLabel);
            productStockDiv.appendChild(stockInputWrapper);
        
            productContent.appendChild(productStockDiv);
        
            //개수제한
            let productLimitDiv = document.createElement('div');
            productLimitDiv.classList.add('productLimitDiv');
            let limitLabel = document.createElement('div');
            limitLabel.textContent = '개수제한';
            let limitInputWrapper = document.createElement('div');
            let limitInput = document.createElement('input');
            limitInput.type = 'text';
            limitInput.classList.add('pLimitInput');
            limitInput.value = `${product.spvo.maxOrderQuantity}`;

            limitInputWrapper.appendChild(limitInput);
            productLimitDiv.appendChild(limitLabel);
            productLimitDiv.appendChild(limitInputWrapper);
        
            productContent.appendChild(productLimitDiv);
        
            boxWrapper.appendChild(productContent);

            saleProductBox.appendChild(boxWrapper);

            saleProductArea.appendChild(saleProductBox);

    });

    //배송방법 불러오기
    originalShipmentList.forEach(shipment=>{
        //shipment : 하나의 배송방법 객체

        let shippingMethodDiv = document.querySelector('.shippingMethodDiv');

        let shippingMethodBox = document.createElement('div');
        shippingMethodBox.classList.add('shippingMethodBox');

        //배송방법
        let shippingName = document.createElement('div');
        shippingName.classList.add('shippingName');
        let nameText = document.createElement('div');
        nameText.classList.add('nameText');
        nameText.textContent = '배송 방법 이름';
        let nameInputDiv = document.createElement('div');
        let shippingMethodInput = document.createElement('input');
        shippingMethodInput.type = 'text';
        shippingMethodInput.classList.add('shippingMethodInput');
        shippingMethodInput.name = 'shippingMethod';
        shippingMethodInput.id = 'shippingMethod';
        shippingMethodInput.placeholder = '배송방법을 입력해주세요.';
        shippingMethodInput.value = `${shipment.shippingMethod}`;

        nameInputDiv.appendChild(shippingMethodInput);
        shippingName.appendChild(nameText);
        shippingName.appendChild(nameInputDiv);
    
        shippingMethodBox.appendChild(shippingName);
    
        //배송가격
        let shippingPrice = document.createElement('div');
        shippingPrice.classList.add('shippingPrice');
        let priceText = document.createElement('div');
        priceText.classList.add('priceText');
        priceText.textContent = '배송비';
        let priceInputDiv = document.createElement('div');
        let shippingCostInput = document.createElement('input');
        shippingCostInput.type = 'text';
        shippingCostInput.classList.add('shippingCostInput');
        shippingCostInput.name = 'shippingCost';
        shippingCostInput.id = 'shippingCost';
        shippingCostInput.value = `${shipment.shippingCost}`;
        priceInputDiv.appendChild(shippingCostInput);
        priceInputDiv.appendChild(document.createTextNode(' 원'));
        shippingPrice.appendChild(priceText);
        shippingPrice.appendChild(priceInputDiv);
    
        shippingMethodBox.appendChild(shippingPrice);
    
        //삭제버튼
        let shippingMethodDelete = document.createElement('div');
        shippingMethodDelete.classList.add('shippingMethodDelete');
        let deleteButton = document.createElement('button');
        deleteButton.type = 'button';
        deleteButton.classList.add('shippingDeleteBtn');
        deleteButton.textContent = 'X';
        shippingMethodDelete.appendChild(deleteButton);
    
        shippingMethodBox.appendChild(shippingMethodDelete);
    
        shippingMethodDiv.appendChild(shippingMethodBox);
    })
}


document.getElementById('thumbnailArea').addEventListener('click', function(){
    document.getElementById('thumbnailFile').click();
});

const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$", "i");

//썸네일 이미지 불러오기
function showThumbnailImg(event){
    const thumbnailArea = document.getElementById('thumbnailArea');

    //기존 이미지 비우기
    thumbnailArea.innerHTML = '';

    const file = event.target.files[0];

    //파일이 없으면 종료(파일이 선택되지 않았을경우 종료)
    if(!file){
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }
    //이미지 파일만 업로드할 수 있게
    if(!regExpImg.test(file.name)){
        alert("이미지 파일만 업로드할 수 있습니다.");
        event.target.value = "";
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }
    
    const reader = new FileReader();
    reader.onload = function(event){
        const img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        img.style.width = "100%"; //부모 너비에 맞춰서
        img.style.height = "100%";
        img.style.objectFit = "cover"; // 이미지 비율 유지하면서 크기 조정
        thumbnailArea.appendChild(img);
    };
    if(file){
        reader.readAsDataURL(file);
    }
}

document.querySelector('.saleProductArea').addEventListener('click', function(event) {
    // 이벤트가 발생한 요소가 .productImg 클래스인지 확인
    if (event.target.closest('.productImg')) {
        const wrapper = event.target.closest('.productImgWrapper');
        if (wrapper) {
            const productImgFile = wrapper.querySelector('.productImgFile');
            if (productImgFile) {
                productImgFile.click();
            }
        }
    }else if(event.target.closest('.productDelBtn')){
        event.preventDefault();
        //console.log("상품삭제버튼");
        //삭제버튼이면 box개수 확인하고 삭제
        let productBoxes = document.querySelectorAll('.saleProductBox');

        if(productBoxes.length>1){
           let productBox = event.target.closest('.saleProductBox');
           productBox.remove();
        }else {
            alert("상품은 한개 이상이어야 합니다.");
        }

    }
});

//상품 이미지 불러오기
function showProductImg(event){

    //target의 부모요소 찾기
    const wrapper = event.target.closest('.productImgWrapper');
    if(wrapper){
        //부모요소 안에 productImg 찾기
        const productImg = wrapper.querySelector('.productImg');

        productImg.innerHTML = '';

        const file = event.target.files[0];
        
        if(!regExpImg.test(file.name)){
            alert("이미지 파일만 업로드할 수 있습니다.");
            event.target.value = "";
            productImg.innerHTML = '<i class="fa-regular fa-image"></i>';
            return;
        }
        const reader = new FileReader();
        reader.onload = function(event){
            const img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.style.width = "100%";
            img.style.height = "100%";
            img.style.objectFit = "cover";
            img.style.borderRadius = "8px";
            productImg.appendChild(img);
        };

        if(file){
            reader.readAsDataURL(file);
        }
    }


}

//상품 추가
let addBtn = document.getElementById('productAddBtn');

addBtn.addEventListener('click', (event)=>{
    event.preventDefault();

    let productArea = document.querySelector('.saleProductArea');
    let productBox = document.querySelector('.saleProductBox');

    let newProductBox = productBox.cloneNode(true);

    //newProductBox.querySelector('.productImg').value = '';
    //newProductBox.querySelector('.productImgFile').value = '';
    //value는 input이나 textarea같은 폼요소에만 적용되므로 이미지 속성은 따로 초기화 해줘야함

    let pImg = newProductBox.querySelector('.productImg img');
    if(pImg !== null){
        //만약 첫번째 box에서 이미지를 넣었으면
        pImg.removeAttribute('src');
    }else{
        //첫번째 box에서 이미지를 안넣었으면 src null이라고 못알아들음
        //그럼 아이콘도 같이 넣어주기
    }

    newProductBox.querySelector('.pNameInput').value = '';
    newProductBox.querySelector('.pPriceInput').value = '';
    newProductBox.querySelector('.pStockInput').value = '';
    newProductBox.querySelector('.pLimitInput').value = '';

    productArea.appendChild(newProductBox);

})

let productList = [];


function sendProductList() {
    // FormData 선언
    let formData = new FormData();

    // productList 생성
    let productList = [];

    document.querySelectorAll('.saleProductBox').forEach((box, index) => {
        let product = {
            spname: box.querySelector('.pNameInput').value,
            spprice: box.querySelector('.pPriceInput').value,
            stock: box.querySelector('.pStockInput').value,
            maxOrderQuantity: box.querySelector('.pLimitInput').value,
            originalImagePath: box.querySelector('.productImg img') ? 
                box.querySelector('.productImg img').src : "" // 기존 이미지 경로 추가
        };
        productList.push(product);
    
        // 이미지 파일 추가
        let imgFileInput = box.querySelector('.productImgFile');
        if (imgFileInput && imgFileInput.files.length > 0) {
            formData.append("imageFiles", imgFileInput.files[0]);
        } else {
            // 이미지가 없으면 기존 경로 전송
            formData.append("originalImagePaths", product.originalImagePath);
        }
    });
    
    // JSON 데이터 추가
    formData.append("productList", new Blob([JSON.stringify(productList)], { type: "application/json" }));

    //sno 같이 보내기
     formData.append("sno", realSno);
    //console.log("sno 값:", formData.get("sno"));

    return new Promise((resolve,reject) =>{
        $.ajax({
            url: "/salePath/productListUpdate",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                //console.log('서버 응답:', response);
                resolve(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('에러 발생:', errorThrown);
                console.error('상태:', textStatus);
                console.error('응답 상태 코드:', jqXHR.status);
                console.error('응답 텍스트:', jqXHR.responseText);
                reject(error);
            }
        });
    })


}


//summernote
$(document).ready(function() {
	$('#summernote').summernote({
		  lang: "ko-KR",  		//한글
		  height: 500,    		// 에디터 높이 설정
		  minHeight: null,      // 최소 높이
		  maxHeight: null,      // 최대 높이
		  //focus: true,          // 에디터 로딩후 포커스를 맞출지 여부
		  //placeholder: '작성된 내용이 부적절하다고 판단될 시 무통보 삭제 될 수 있습니다.',	//placeholder 설정
          //콜백함수
          callbacks : {
        	  onImageUpload : function (files, editor, welEditable){
        		  for(var i = files.length - 1; i>=0; i--){
        			  //var fileName = files[i].name
        			  //uploadSummernoteImageFile(files[i], this, fileName) fileName이 왜필요하지..?
        			  imageUpload(files[i], this);
        		  }
        	  }
          }
	});
});


function imageUpload(file, el){
    var formData = new FormData();
    formData.append('file', file);

$.ajax({
    data: formData,
    type: "POST",
    dataType: "JSON",
    url: '/salePath/summernote',
    contentType: false,
    processData: false,
    enctype: 'multipart/form-data',
    success: function(data) {
        //console.log(data);
        //console.log(data.url);
        //console.log(data.responseCode);
        if (data.responseCode === 'success') {
            $(el).summernote('insertImage', data.url);
        } else {
            console.log('이미지 업로드 실패 : ', data);
        }
    },
    error: function(jqXHR, textStatus, errorThrown) {
        console.error("AJAX 요청 실패: " + textStatus + ", " + errorThrown);
        console.log(jqXHR.responseText);
    }
});
}

//기간설정부분

$(function(){
    $("#startDay").datepicker({
        dateFormat : "yy-mm-dd",
        minDate : 0, //오늘부터 선택 가능
        //maxDate : "+3M", //오늘부터 3달까지만 선택 가능
        showAnim : "fadeIn", //선택될 때 애니메이션 효과
        //changeMonth : true, //드롭다운으로 선택 가능하게
        //changeYear : true,
        yearSuffix : "년",
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
        monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
        dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
      });
});

$(function(){
  $("#endDay").datepicker({
      dateFormat : "yy-mm-dd",
      minDate : 0, //오늘부터 선택 가능
      maxDate : "+3M", //오늘부터 3달까지만 선택 가능
      showAnim : "fadeIn", //선택될 때 애니메이션 효과
      //changeMonth : true, //드롭다운으로 선택 가능하게
      //changeYear : true,
      yearSuffix : "년",
      monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
      monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
      dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
      dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
  });
});

//startHour

const startHourSelect = document.getElementById("startHour");
const endHourSelect = document.getElementById("endHour");

const startHourValue = String(startHour).padStart(2,'0');
const endHourValue = String(endHour).padStart(2,'0');

for(let i=0; i<24; i++){
  //시간 두자리로 하기 위해 문자 채우기
  //적용할문자열.(채울길이, 채울문자)
  //padStart는 앞부분, padEnd는 뒤를 채움
  const hour = String(i).padStart(2, '0');
  const startOption = document.createElement("option");
  startOption.value = hour;
  //option.textContent = hour+"시";
  startOption.textContent = hour;

  if(hour === startHourValue){
    startOption.selected = true;
  }

  startHourSelect.appendChild(startOption);

  const endOption = document.createElement("option");
  endOption.value = hour;
  //option.textContent = hour+"시";
  endOption.textContent = hour;
  
  if(hour === endHourValue){
    endOption.selected = true;
  }
  endHourSelect.appendChild(endOption);
}

const startMinuteSelect = document.getElementById("startMinute");
const endMinuteSelect = document.getElementById("endMinute");
const startMinuteValue = String(startMinute).padStart(2,'0');
const endMinuteValue = String(endMinute).padStart(2,'0');

for(let i=0; i<60; i++){
  const minute = String(i).padStart(2, '0');
  const startOption = document.createElement("option");
  startOption.value = minute;
  startOption.textContent = minute;

  if(minute === startMinuteValue){
    startOption.selected = true;
  }
  startMinuteSelect.appendChild(startOption);

  const endOption = document.createElement("option");
  endOption.value = minute;
  endOption.textContent = minute;

  if(minute === endMinuteValue){
    endOption.selected = true;
  }
  endMinuteSelect.appendChild(endOption);
}

//배송방법 추가

let methodAddBtn = document.getElementById("shippingMethodAddBtn");

methodAddBtn.addEventListener("click",(event)=>{
    event.preventDefault();
    let shippingMethodDiv = document.querySelector('.shippingMethodDiv');
    let shippingMethodBox = document.querySelector('.shippingMethodBox');

    let newShippingMethodBox = shippingMethodBox.cloneNode(true);

    newShippingMethodBox.querySelector('.shippingMethodInput').value = '';
    newShippingMethodBox.querySelector('.shippingCostInput').value = '0';

    shippingMethodDiv.appendChild(newShippingMethodBox);

})

document.addEventListener('click', (event)=>{
    if(event.target.classList.contains('shippingDeleteBtn')){
        //console.log("버튼눌림!");
        event.preventDefault();

        //현재 남아있는 shippingMethodBox 개수 확인하기
        let shippingBoxes = document.querySelectorAll('.shippingMethodBox');

        if(shippingBoxes.length>1){
            let shippingBox = event.target.closest('.shippingMethodBox');
            shippingBox.remove();
        }else{
            alert('배송 방법은 한개 이상이어야 합니다.');
        }
    }
})

//배송예정 날짜

$(function(){
    $("#shippingExdate").datepicker({
        dateFormat : "yy-mm-dd",
        minDate : 0, //오늘부터 선택 가능
        showAnim : "fadeIn",
        yearSuffix : "년",
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
        monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
        dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
      });
});


//textarea 스크롤바X, 길이 자동늘어나게
function autoResizeTextarea(textarea){
    textarea.style.height = 'auto';
    textarea.style.height = (textarea.scrollHeight) + 'px';
}

let textarea0 = document.getElementById("shippingInstructions");
let textarea1 = document.getElementById("refundMessage");
textarea0.addEventListener('input', function(){
    autoResizeTextarea(this);
})
textarea1.addEventListener('input', function(){
    autoResizeTextarea(this);
})


//택배방법 전송
function sendShippingMethod(){

    //shippingMethod list생성
    let shippingMethod = [];

    //forEach로 shippingBox순회하기
    let shippingMethodBoxes = document.querySelectorAll('.shippingMethodBox');

    shippingMethodBoxes.forEach(box=>{
        let nameValue = box.querySelector(".shippingMethodInput").value;
        let priceValue = box.querySelector(".shippingCostInput").value;

        shippingMethod.push({ shippingMethod:nameValue, shippingCost:priceValue, sno : realSno});
    })

    //console.log(JSON.stringify(shippingMethod, null, 2));
    //console.log(JSON.stringify({shippingMethod}, null, 2));

    return new Promise((resolve, reject)=>{
        $.ajax({
            url : "/salePath/updateSaleShipment",
            type : "post",
            data : JSON.stringify(shippingMethod),
            contentType : "application/json; charset=utf-8",
            success : function(response){
                //console.log("배송방법 업데이트 성공");
                resolve(response);
            },
            error : function(error){
             console.log("배송방법 업데이트 에러발생 : "+error);
             reject(error);
            }
         })
    })

}

//숫자만 입력가능
let accountNumber = document.getElementById("accountNumber");

function number(event) {
    const pattern = /[^0-9]/g;

    if (pattern.test(event.target.value)) {
        alert("숫자만 입력해주세요.");
        event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
    }
}
accountNumber.addEventListener('keyup', number);

let saleProductArea = document.querySelector('.saleProductArea');
saleProductArea.addEventListener('keyup', inputNumber);

function inputNumber(event) {
    const pattern = /[^0-9]/g;

    if(event.target.classList.contains('pPriceInput') ||
    event.target.classList.contains('pStockInput')||
    event.target.classList.contains('pLimitInput')){
        if (pattern.test(event.target.value)) {
            alert("숫자만 입력해주세요.");
            event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
        }
    }
}

let shippingMethod = document.querySelector('.shippingMethod');
shippingMethod.addEventListener('keyup', (event)=>{
    //console.log(event.target);
    const pattern = /[^0-9]/g;
    if(event.target.classList.contains('shippingCostInput')){
        if (pattern.test(event.target.value)) {
            alert("숫자만 입력해주세요.");
            event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
        }
    }
})

//빈칸있으면 전송 안되게 막기
let submitBtn = document.getElementById('submitBtn');

function checkProduct(){
    const productBoxes = document.querySelectorAll('.saleProductBox');

    for(let box of productBoxes){
        const inputs = box.querySelectorAll('input[type="text"]');

        for(let input of inputs){
            if(input.value.trim()==="" || input.value.trim().length===0){
                alert("상품의 값을 모두 입력해주세요.");
                return false;
            }
        }
    }

    return true;
}

function checkShipment(){
    const shipmentBoxes = document.querySelectorAll('.shippingMethodBox');

    for(let box of shipmentBoxes){
        const inputs = box.querySelectorAll('input[type="text"]');

        for(let input of inputs){
            if(input.value.trim() === "" || input.value.trim().length === 0){
                alert("배송 정보를 모두 입력해주세요.");
                return false;
            }
        }
    }

    return true;
}

function validateForm(){

    const category = document.getElementById("categorySelect");
    if(category.value === ""){
        alert("카테고리를 선택해주세요.");
        return false;
    }

    const startDay = document.getElementById("startDay");
    if(startDay.value.trim() === "" || startDay.value.trim().length === 0){
        alert("시작날짜를 선택해주세요.");
        return false;
    }

    const startHour = document.getElementById("startHour");
    if(startHour.value === ""){
        alert("시작시간을 선택해주세요.");
        return false;
    }

    const startMinute = document.getElementById("startMinute");
    if(startMinute.value === ""){
        alert("시작시간을 선택해주세요.");
        return false;
    }
    
    const endDay = document.getElementById("endDay");
    if(endDay.value.trim() === "" || endDay.value.trim().length === 0){
        alert("종료날짜를 선택해주세요.");
        return false;
    }

    const endHour = document.getElementById("endHour");
    if(endHour.value === ""){
        alert("종료시간을 선택해주세요.");
        return false;
    }

    const endMinute = document.getElementById("endMinute");
    if(endMinute.value === ""){
        alert("종료시간을 선택해주세요.");
        return false;
    }

    const stitle = document.getElementById("stitle");
    if(stitle.value.trim() === "" || stitle.value.trim().length === 0){
        alert("제목을 입력해주세요.");
        return false;
    }

    const sdetail = document.getElementById("summernote");
    if(sdetail.value.trim() === "" || sdetail.value.trim().length === 0){
        alert("내용을 입력해주세요.");
        return false;
    }

    const bankName = document.getElementById("bankName");
    const accountNumber = document.getElementById("accountNumber");
    const accountHolder = document.getElementById("accountHolder");
    if(bankName.value.trim() === "" || bankName.value.trim().length === 0){
        alert("은행이름을 입력해주세요.");
        return false;
    }

    if(accountNumber.value.trim() === "" || accountNumber.value.trim().length === 0){
        alert("계좌번호를 입력해주세요.");
        return false;
    }

    if(accountHolder.value.trim() === "" || accountHolder.value.trim().length === 0){
        alert("예금주를 입력해주세요.");
        return false;
    }

    if(!checkProduct()){
        return false;
    }

    if(!checkShipment()){
        return false;
    }

    const shippingInstructions = document.getElementById("shippingInstructions");
    if(shippingInstructions.value.trim()==="" || shippingInstructions.value.trim().length === 0){
        alert("배송 안내사항을 입력해주세요.");
        return false;
    }

    const refundMessage = document.getElementById("refundMessage");
    if(refundMessage.value.trim() === "" || refundMessage.value.trim().length === 0){
        alert("환불 및 교환 안내사항을 입력해주세요.");
        return false;
    }

    return true;

}

submitBtn.addEventListener('click',(event)=>{
    if(!validateForm()){
        event.preventDefault();
    }else{
        event.preventDefault();
        Promise.all([sendProductList(), sendShippingMethod()])
            .then(()=>{
                document.getElementById('saleForm').submit();
            })
            .catch((error)=>{
                console.error("요청 중 오류 발생 : ",error);
                alert("문제가 발생했습니다. 다시 시도해주세요.");
            })
        
        
    }
})


