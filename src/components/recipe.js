
import { Component } from "react";
import axios from "axios";
import Review from "./review";


class Recipe extends Component{



    constructor(props){
        super(props);
        if(props.isSelectedRecipe){
            this.state={
            base64Img: props.recipe.image,
            title: props.recipe.title,
            steps: props.recipe.steps,
            like: props.recipe.likeNumber,
            idUser: props.recipe.idUser,
            idRecipe: props.recipe.idRecipe,
            ingredients: props.recipe.ingredientsList,
            areLoaded: false,
            showReview: false
            }
        }
        else {
            this.state={
                base64Img: "",
                title: "",
                steps: "",
                like: -1,
                idUser: -1,
                idRecipe: -1,
                ingredients: [],
                areLoaded: false,
                showReview: false
            };
        }
        this.fetchData = this.fetchData.bind(this);
        this.addIngredientsToRecipe = this.addIngredientsToRecipe.bind(this);
        if(!this.props.isSelectedRecipe)this.fetchData();
        this.ingredientsList = this.ingredientsList.bind(this);
        this.handleReviewShowHide = this.handleReviewShowHide.bind(this);
    }

    handleReviewShowHide(){
        this.setState(prev=>({showReview: !prev.showReview}));
    }

    ingredientsList(names){
        return (<li className="list-group-item">
                {names[0]}
                {names[1]}
            </li>);
    }


    addIngredientsToRecipe(il){
        const names = il.map(function(item) {
            return item['name'];
          });
        return names;
    }

    fetchData(){
        if(!this.state.areLoaded){
            axios.get('http://localhost:8000/recipe/random').
        then(res=>{
            this.setState({base64Img: res.data.image});
            this.setState({idUser: res.data.idUser});
            this.setState({idRecipe: res.data.idRecipe});
            this.setState({ingredients: res.data.ingredients});
            this.setState({title: res.data.title});
            this.setState({steps: res.data.steps});
        });
        this.setState({areLoaded: true});
        }
    }







    render(){
        if(this.props.isSelectedRecipe){
            return(
                <div className="card" style={{alignItems: "center"}}>
                <img src={`data:image/jpeg;base64,${this.state.base64Img}`} style={{alignItems: "center", width: "50%", height: "50%"}} className="card-img-top" alt="..."></img>
                <div className="card-body">
                    <h5 className="card-title">{this.state.title}</h5>
                    <ul className="list-group"> <b className="display-3">Ingredienti:</b><br></br>
                        <>
                        {this.props.ingredientsList.map((i)=>(<li key={i.id} className="list-group-item" style={{backgroundColor: '#7A7575'}}>{i.name}
                            </li>))}
                        </>
                    </ul>
                    <p className="card-text">{this.state.steps}</p>
                    <br></br>
                    
                </div>
            </div>
            );
        }else return(
            <div className="card" style={{alignItems: "center"}}>
                <img src={`data:image/jpeg;base64,${this.state.base64Img}`} style={{alignItems: "center", width: "50%", height: "50%"}} className="card-img-top" alt="..."></img>
                <div className="card-body">
                    <h5 className="card-title">{this.state.title}</h5>
                    <ul className="list-group"> <b className="display-3">Ingredienti:</b><br></br>
                        <>
                        {this.state.ingredients.map((i)=>(<li key={i.id} className="list-group-item" style={{backgroundColor: '#7A7575'}}>{i.name}
                            </li>))}
                        </>
                    </ul>
                    <p className="card-text">{this.state.steps}</p>
                    <button className="btn btn-dark" onClick={this.handleReviewShowHide}><small className="text-muted">visualizza recensioni!</small></button>
                    <br></br>
                    {this.state.showReview ? <Review idRecipe={this.state.idRecipe}/>:null}
                </div>
            </div>
        );
    }
}





export default Recipe;
