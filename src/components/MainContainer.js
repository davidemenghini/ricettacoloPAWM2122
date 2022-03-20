import { Component } from "react";

import LoginScreen from "./LoginScreen";
import Recipe from "./recipe";
import AddRecipe from "./addRecipe";
import axios from "axios";

const delay = ms => new Promise(res => setTimeout(res, ms));




export default class MainContainer extends Component{


        constructor(props){
            super(props);
            this.state = {
                recipeAreVisibles : true,
                loginIsVisible : false,
                showAddRecipe : false,
                showSearchRecipe: false,
                searchedRecipe: [{}],
                inputValue: "",
            };
            this.handleLoginPressed = this.handleLoginPressed.bind(this);
            this.handleAddRecipeButton = this.handleAddRecipeButton.bind(this);
            this.handleSearchBar = this.handleSearchBar.bind(this);
            this.handleKeyPress = this.handleKeyPress.bind(this);
        }


        handleKeyPress(e){
            if(e.key === 'Enter'){
                console.log(this.state.recipeAreVisibles);
                this.setState(prev=>({recipeAreVisibles: false}));
                console.log(this.state.recipeAreVisibles);
                var obj = {
                    title:this.state.inputValue
                }
                var json = JSON.stringify(obj);
                console.log(json);
                axios({
                    method: "POST",
                    url:'http://127.0.0.1:8000/recipe/search',
                    headers:{'Content-type': 'application/json'},
                    withCredentials:true,
                    data:json
                }).then((resp)=>{
                    console.log(resp.data);
                    this.setState({searchedRecipe: resp.data});
                    this.setState({showSearchRecipe:true});
                }
                ).catch(function(error){
                    console.log(error);
                });
            }else{
                this.setState({recipeAreVisibles: true});
                console.log('è null');
            }
        }

        handleSearchBar(e){
            var value = e.target.value;
            if(value!=""){
                this.setState({inputValue: e.target.value});
            }else this.setState({showSearchRecipe:false});
            
        }

        handleAddRecipeButton(){
            this.setState(prev=>({showAddRecipe: !prev.showAddRecipe}));
            this.setState(prev=>({recipeAreVisibles: !prev.recipeAreVisibles}));
            this.setState(prev=>({loginIsVisible: false}));
        }

        
        handleLoginPressed() {
            this.setState(prev=>({loginIsVisible: !prev.loginIsVisible}));
            this.setState(prev=>({recipeAreVisibles: !prev.recipeAreVisibles}));
            this.setState(prev=>({showAddRecipe: !prev.showAddRecipe}))
        }



       






        




    render(){
        if(this.state.showSearchRecipe===true && this.state.searchedRecipe){
            return(
                
                <div>
                    <input type="search" id="form1" className="form-control" placeholder="cerca una ricetta..." aria-label="Search" onChange={(e)=>this.handleSearchBar(e)} onKeyUp={(e)=>this.handleKeyPress(e)}/>
                    <>
                    {
                    this.state.searchedRecipe.map((data,i)=>(
                        <Recipe key={i} isSelectedRecipe={true} recipe={data} ingredientsList={data.ingredients} style={{backgroundColor: '#7A7575'}}>
                        </Recipe>))
                    }
                        
                    
                        
                    </>
                </div>
            )
        }else{
            console.log(this.state.showSearchRecipe);
            return(
                <div className="text-center container" >
                    {!this.state.loginIsVisible ? <div className="form-outline">
                        <input type="search" id="form1" className="form-control" placeholder="cerca una ricetta..." aria-label="Search" onChange={(e)=>this.handleSearchBar(e)} onKeyUp={(e)=>this.handleKeyPress(e)}/>
                    </div> : null}
                    {this.state.loginIsVisible ? <LoginScreen loadScreen={this.handleLoginPressed}/> : null}
                    {this.state.recipeAreVisibles ? <Recipe isSelectedRecipe={false}/> : null}
                    <br></br>
                    <button className="btn btn-success align-baseline" onClick={(this.handleAddRecipeButton)}>Aggiugni una ricetta!</button>
                    {this.state.showAddRecipe ? <AddRecipe show = {this.handleAddRecipeButton}></AddRecipe>: null}
                    <br></br>
                    <br></br>
                    {!this.state.loginIsVisible ? <button onClick={(this.handleLoginPressed)} className="text-center btn btn-primary ">Login</button> : null}
                    
                    
                </div>

            )
        } 
        
    }
    

    



}


