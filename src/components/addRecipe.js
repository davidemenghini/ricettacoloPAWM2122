import axios from "axios";
import { Component } from "react";
import Cookie from "universal-cookie";




const convertToBase64 = (file) =>{
    return new Promise((resolve,reject)=>{
        const fileReader = new FileReader();
        fileReader.readAsDataURL(file);
        fileReader.onload = ()=>{
            resolve(fileReader.result);
        };
        fileReader.onerror = (error)=>{
            console.log(error);
            reject(error);
        }
    });
}   



const uploadImage = async (e) =>{
    var file = e.target.files[0];
    var base64 =  await convertToBase64(file);
    console.log(base64);
    return base64.toString();
}


export default class AddRecipe extends Component{



    constructor(props){
        super(props);
        this.state ={
            ingredientList: [],
            selectedIngredients: [],
            title: "",
            description: "",
            image: null 
        }
        this.handleAddSelectedIngredients = this.handleAddSelectedIngredients.bind(this);
        this.handleTitleInput = this.handleTitleInput.bind(this);
        this.handleImageInput = this.handleImageInput.bind(this);
        this.handleDescriptionInput = this.handleDescriptionInput.bind(this);
        this.handleButtonClick = this.handleButtonClick.bind(this);
        this.fetchIngredientList =  this.fetchIngredientList.bind(this);
        this.fetchIngredientList();
    }


    





    handleButtonClick(){
        var access_token = new Cookie();
        var at = access_token.get("access_token");

        console.log(this.state);
        if(sessionStorage.getItem('idUser')==null || at==''){
            alert('devi effettuare il login!!');
        }else if(this.state.title!=="" && this.state.description!=="" && this.state.ingredientList.length>0 && this.state.image!==""){
            console.log(this.state.image+' image');
            
            const obj = {
                title:this.state.title,
                description: this.state.description,
                image: this.state.image,
                ingredientList: this.state.selectedIngredients,
                idUser: sessionStorage.getItem('idUser'),
                access_token: at
            };
            const json = JSON.stringify(obj);
            console.log(json);
            console.log('ciaoo');
            axios({
                method: "POST",
                url:'http://127.0.0.1:8000/recipe/addRecipe',
                headers:{'Content-type': 'application/json'},
                withCredentials:true,
                data:json
            }).then(()=>
                this.goBack()
            ).catch(function(error){
                alert('errore nell invio di dati al server!');
                console.log(error);
            });
        }else alert('controlla che tutti i campi siano inseriti!!');
    }

    goBack = () => {
        this.props.show();
    }


    fetchIngredientList(){
        axios({
            method: 'GET',
            url: 'http://127.0.0.1:8000/recipe/ingredientsList',
            headers:{'Content-type': 'application/json'},
            withCredentials: true
        }).then(resp=>{
            this.setState({ingredientList: resp.data});
        }).catch(function (error){
            console.log(error);
        });
    }


    handleDescriptionInput(e){
        this.setState({description: e.target.value});
    }


    handleTitleInput(e){
        this.setState({title: e.target.value});
    }


    handleImageInput(e){
        var file = e.target.files[0];
        var base64;
        const fileReader = new FileReader();
        fileReader.readAsDataURL(file);
        fileReader.onload = ()=>{
            base64 = fileReader.result;
            console.log(base64);
            var strImage = base64.replace(/^data:image\/[a-z]+;base64,/, "");
            this.setState({image: strImage});
        };
        
        
    }

    


   


    handleAddSelectedIngredients(e){
        console.log(e.target.value);
        var selected = e.target.value;
        this.setState(prev => ({
            selectedIngredients: [...prev.selectedIngredients, selected]
        }));
        console.log(this.state.selectedIngredients);
    }

    



    



    render(){
        if(this.state.ingredientList.length<0){
            this.fetchIngredientList();
        }
        return(
        <div>
            <br></br>
            <br></br>
            <br></br>

            <div className="mb-3"  style={{backgroundColor: 'grey'}}>
                <label htmlFor="exampleFormControlInput1" className="form-label">Nome della ricetta</label>
                <input onChange={(e)=>this.handleTitleInput(e)} type="text" className="form-control" id="exampleFormControlInput1" placeholder="titolo..."></input>
            </div>
            <div className="mb-3"  style={{backgroundColor: 'grey'}}>
                <label htmlFor="exampleFormControlTextarea1" className="form-label">Preparazione</label>
                <textarea className="form-control" id="exampleFormControlTextarea1" rows="3" onChange={(e)=>this.handleDescriptionInput(e)}></textarea>
            </div>
            <div className="mb-3"  style={{backgroundColor: 'grey'}}>
                <label htmlFor="exampleFormControlTextarea1" className="form-label">seleziona un immagine della ricetta</label>
                <input onChange={(e)=>this.handleImageInput(e)} type="file" className="form-control" id="exampleFormControlTextarea1" ></input>
            </div>
            <div className="mb-3"  style={{backgroundColor: 'grey'}}>
                <label htmlFor="exampleFormControlTextarea1" className="form-label">seleziona gli ingredienti della ricetta!</label>
                <select className="form-select" onChange={(e)=>this.handleAddSelectedIngredients(e)}>
                    <>{
                        
                    this.state.ingredientList.map((data,id)=>(
                    
                    <option id={id} key={id} value={data}>
                        {data}
                    </option>))
                    }</>
                </select>
                <label className ="form-label">ingredienti selezionati: </label>
                <>
                
                    {this.state.selectedIngredients ?  this.state.selectedIngredients.map((data,i)=>(
                    <p key={i} className="list-group-item" style={{backgroundColor: '#7A7575'}}>
                        {data.toString()}
                    </p>)): null}
                </>
            </div>
            <div className="mb-3" >
                    <button type="button" className="btn btn-success" onClick={this.handleButtonClick}>inserisci ricetta!</button>
            </div>
            
        </div>    
        );
    }


}



    


