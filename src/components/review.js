import axios from "axios";
import React, { Component } from "react";
import Cookie from "universal-cookie";





export default class Review extends Component{

    


    constructor(props){
        super(props);
        this.fetchReviews = this.fetchReviews.bind(this);
        console.log(this.props.idRecipe);
        this.state = {
            idRecipe: this.props.idRecipe,
            reviewList: [{}],
            showAddReview: false,
            titleReview: "",
            descriptionReview:""
        }
        console.log(this.state);
        this.addReviews = this.addReviews.bind(this);
        this.handleAddLike = this.handleAddLike.bind(this);
        this.handleAddDisike = this.handleAddDislike.bind(this);
        this.handleTitleInput = this.handleTitleInput.bind(this);
        this.handleAddReviewClick = this.handleShowAddReviewClick.bind(this);
        this.handleDescriptionInput = this.handleDescriptionInput.bind(this);
    }



    



    handleDescriptionInput(e){
        this.setState({descriptionReview: e.target.value});
    }


    handleShowAddReviewClick(e){
        console.log(this.state.showAddReview+" valore di showaddreview");
        this.setState(prev=>({showAddReview: !prev.showAddReview}));
    }



    handleTitleInput(e){
        this.setState({titleReview: e.target.value});
    }



    handleAddLike(event,idReview){
        var valueButton = event.target.innerHTML;
        var idUser = sessionStorage.getItem('idUser');
        var access_token = new Cookie();
        var at = access_token.get('access_token');
        var d;
        
        if(at!=="" && idUser!==null && idReview!==""){
            d = {
                idUser: idUser.toString(),
                idReview: idReview.toString(),
                access_token: at.toString()
            };
            var j = JSON.stringify(d);
            if(valueButton=="mi piace!"){
                axios({
                    method:'POST',
                    url:'http://127.0.0.1:8000/review/addLike',
                    headers:{'Content-type': 'application/json'},
                    withCredentials: true,
                    data: j
                }).then(res=>{
                    this.fetchReviews();
                }).catch(function(error){
                    console.log(error);
            });
            }else{
                axios({
                    url:'http://127.0.0.1:8000/review/removeLike',
                    method:"POST",
                    headers:{'Content-type': 'application/json'},
                    withCredentials: true,
                    data:j
                }).then(res=>{
                    this.fetchReviews();
                }).catch(function (error){
                    console.log(error);
                });
            }
            
        }else{
            alert('devi effettuare il login per mettere un mi piace!!');
        }
    }

    handleAddDislike(event,idReview,index){
        if(sessionStorage.getItem("idUser")!==null){
            var valueButton = event.target.innerHTML;
            var idUser = sessionStorage.getItem("idUser");
            var access_token = new Cookie();
            var at = access_token.get("access_token");
            var d = {
                idUser: idUser.toString(),
                idReview: idReview.toString(),
                access_token: at
            };
            
            var j = JSON.stringify(d);
            if(valueButton=="non mi piace!" && (at!=="" && idUser!==null && idReview!=="")){
                axios({
                    method:'POST',
                    url:'http://127.0.0.1:8000/review/addDislike',
                    headers:{'Content-type': 'application/json'},
                    withCredentials: true,
                    data: j
                }).then(res=>{
                    this.fetchReviews();
                }).catch(function(error){
                    console.log(error);
                });
            }else if(at!=="" && idUser!==null && idReview!==""){
                axios({
                    method:'POST',
                    url:'http://127.0.0.1:8000/review/removeDislike',
                    headers:{'Content-type':'application/json'},
                    withCredentials: true,
                    data:j
                }).then(resp=>{
                    this.fetchReviews();
            }).catch(function(error){
                console.log(error);
            });
        }
        }else alert('devi effettuare il login per mettere un non mi piace!!');
    }



    componentDidMount(){
        this.fetchReviews();
    }

    fetchReviews(props){
        var cookie = new Cookie();
        var idUser = sessionStorage.getItem('idUser');
        var obj;
        if(idUser==null){
            idUser = -1;
        }
        obj = {idRecipe: this.props.idRecipe.toString(),
        idUser: idUser.toString(),
        access_token: cookie.get('access_token')}
        var json = JSON.stringify(obj);
        axios({
            method: 'post',
            url: 'http://127.0.0.1:8000/review/allReview',
            headers:{'Content-type': 'application/json'},
            withCredentials: true,
            data: json
        }).then(resp=>{
            this.setState({reviewList: resp.data});
        }).catch(function (error){
            console.log(error);
        });
    }


    addReviews(){
        console.log('sto dentro');
        var access_token = new Cookie();
        var at = access_token.get("access_token");
        var idU = sessionStorage.getItem('idUser');
        if(idU!==null && at!==''){
            const obj = {
                title: this.state.titleReview,
                description:this.state.descriptionReview,
                idRecipe:this.state.idRecipe,
                idUser:idU,
                access_cookie:at
            };
            var json = JSON.stringify(obj);
            axios({
                url:"http://127.0.0.1:8000/review/addReview",
                method:"POST",
                withCredentials:true,
                headers:{'Content-type': 'application/json'},
                data:json
            }).then(resp=>{
                this.handleShowAddReviewClick();
            }).catch(function(error){
                console.log(error);
            });
        }else alert('devi effettuare il login per aggiungere una recensione!!');
    }

    render(){
        if(!this.state.showAddReview){
            return(
                <div >
                    <br></br>
                    <br></br>
                    <button className="btn btn-success" onClick={(e)=>this.handleShowAddReviewClick(e)}>aggiungi una recensione!</button>
                    <br></br>
                    <br></br>
                    <>{
                        this.state.reviewList.map((data,i)=>(
                        <div  style={{backgroundColor: '#B3B3B3'}} key={i} className="card">
                            <div className="card-header">
                            </div>
                            <div className="card-body" style={{backgroundColor: '#8DD8CC'}}>
                                <h5 className="card-title h3">{data.title}</h5>
                                <p className="card-text" style={{
                                    backgroundColor:'#544F4F',
                                    color:'#ffffff'
                                    }} >{data.description}</p>
                                <label className="fw-normal" style={{backgroundColor: 'green', color:'white'}}>mi piace: {data.likesNumber}</label>
                                <br></br>
                                <br></br>
                                <label className="fw-normal" style={{backgroundColor: '#990000', color:'white'}}>non mi piace: {data.dislikesNumber}</label>
                                <br></br>
                                <br></br>
                                {!data.likedUser ?  <button href="#" className="btn btn-success" value={"mi piace!"} onClick={(event)=>this.handleAddLike(event,data.idReview, i)}>mi piace!</button>: 
                                <button href="#" className="btn btn-success" value={"annulla mi piace"}onClick={(event)=>this.handleAddLike(event,data.idReview,i)}>annulla mi piace!</button>}
        
                                {!data.dislikedUser ? <button href="#" className="btn btn-danger" value={"non mi piace!"} onClick={(event)=>this.handleAddDislike(event,data.idReview,i)}>non mi piace!</button>:
                                <button href="#" value={"annulla non mi piace!"}className="btn btn-danger" onClick={(event)=>this.handleAddDislike(event,data.idReview,i)}>annulla non mi piace!</button>}
                            </div>
                            <br></br>
                            <br></br>
                            <br></br>           
                        </div>
                ))}
                </>
                </div>
                );
        }else{
            return (
                <div>
                    <br></br>
                    <br></br>
                    <button className="btn btn-warning" onClick={(e)=>this.handleShowAddReviewClick(e)}>torna indietro!</button>
                    <br></br>
                    <br></br>
                    <div class="mb-3"  style={{backgroundColor: 'grey'}}>
                        <label htmlFor="exampleFormControlInput1" class="form-label">Titolo della recensione</label>
                        <input onChange={(e)=>this.handleTitleInput(e)} type="text" class="form-control" id="exampleFormControlInput1" placeholder="titolo..."></input>
                    </div>
                    <div class="mb-3"  style={{backgroundColor: 'grey'}}>
                        <label htmlFor="exampleFormControlTextarea1" class="form-label">Che cosa ne pensi della ricetta?</label>
                        <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" onChange={(e)=>this.handleDescriptionInput(e)}></textarea>
                    </div>
                    <div class="mb-3" >
                        <button type="button" class="btn btn-success" onClick={()=>this.addReviews()}>inserisci recensione!</button>
                    </div>
                </div>
            );
        }
        
    }
}