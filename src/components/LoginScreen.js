
import axios from "axios";
import { Component, useCallback } from "react";
import Cookie from 'universal-cookie'



export default class LoginScreen extends Component{


    constructor(props){
        super(props);
        this.state = {
            idUser : "",
            pass : ""
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleChangePass = this.handleChangePass.bind(this);
        this.handleChangeUser = this.handleChangeUser.bind(this);
        this.tryLogin = this.tryLogin.bind(this);
    }


    goBack = () => {
        this.props.loadScreen();
    }

    



    tryLogin(){
        var json = JSON.stringify(this.state);
        axios({
            method: 'post',
            url: 'http://127.0.0.1:8000/user/tryLogin',
            headers:{'Content-type': 'application/json'},
            withCredentials: true,
            data: json
        })
        .then(res=>{
            const cookies = new Cookie();
            cookies.set("access_token",res.data.access_token,{
                maxAge: new Date(Date.now()+600000),
                path: '/'});
            sessionStorage.setItem('idUser',res.data.idUser);
            sessionStorage.setItem('user',res.data.user);
            if(res.data.access_token!=null && res.data.idUser!=null && res.data.user!=null)this.goBack();
            
        })
        .catch(function (error){
            console.log(error);
        })
        
    }

    handleSubmit(props){
        this.tryLogin(props);
    }

    handleCancel(){
       this.setState(prev=>({loadScreen: false}));
    }

    handleChangeUser(e){
        this.setState({idUser: e.target.value});
        
    }

    handleChangePass(e){
        this.setState({
            pass: e.target.value
        });
    }

    

    

    render(){
        return(
            <div>
               <div className="form-group">
                    <label htmlFor="exampleInputEmail1"><b>USERNAME</b></label>
                    <input onChange={this.handleChangeUser} type="username" className="form-control" aria-describedby="emailHelp" placeholder="username..."></input>
                </div>
                <br></br>
                <div className="form-group">
                    <label htmlFor="exampleInputPassword1"><b>PASSWORD</b></label>
                    <input onChange={this.handleChangePass}  type="password"  className="form-control" placeholder="Password..."></input>
                </div>
                    <br></br>
                        <button type="button" className="btn btn-success" onClick={this.handleSubmit}>Login</button>
                        <br></br>
                        <br></br>
                        <button type="button" className="btn btn-danger" onClick={this.props.loadScreen}>Annulla</button>
            </div>
        );
    }
}


