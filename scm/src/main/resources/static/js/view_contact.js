console.log("View Contact modal");
const baseURL = "http://localhost:8081";
const viewContactModal = document.getElementById('view_contact_modal');

//options
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal,options,instanceOptions);

//when button click open the modal or page
function openContactModal(){
    contactModal.show();
}

function closeContactModal(){
    contactModal.hide();
}

async function loadContactData(id){
    console.log(id);
    try {
        const response = await fetch(`${baseURL}/api/contacts/${id}`);
        const data = await response.json();

        console.log(data);
        // console.log(data.name);

        document.querySelector("#contact_name").innerHTML=data.name;
        document.querySelector("#contact_email").innerHTML=data.email;
        // document.querySelector("#contact_image").innerHTML=data.picture
        document.getElementById("contact_image").src=data.picture
        document.querySelector("#contact_address").innerHTML=data.address;
        document.querySelector("#contact_description").innerHTML=data.description;
        document.querySelector("#contact_email").href=data.email;
        document.querySelector("#contact_email").innerHTML=data.email;
        
        openContactModal();
    } catch (error) {
        console.log(error);
    }
}

//delete contacts using a dialog

async function deleteContact(id){
    Swal.fire({
  title: "Do you want to delete the contact?",
  icon:"warning",
  showCancelButton: true,
  confirmButtonText: "Delete",
  
}).then((result) => {
  if (result.isConfirmed) {
    Swal.fire("Deleted!", "", "success");
    const url = `${baseURL}/user/contacts/delete/${id}` ;
    window.location.replace(url); 
  }
});
}
