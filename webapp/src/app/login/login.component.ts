import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Location} from "@angular/common";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private httpClient: HttpClient,
              private location: Location) { }
  @ViewChild('username') usernameRef: ElementRef;
  @ViewChild('password') passwordRef: ElementRef;

  ngOnInit(): void {

  }

  login() {
    this.httpClient.post('http://localhost:8080/api/login' ,{username: this.usernameRef.nativeElement.value, password: this.passwordRef.nativeElement.value})
      .subscribe(response => {
        console.log(response);
        if(response) {
          var token = window.btoa(this.usernameRef.nativeElement.value+':'+this.passwordRef.nativeElement.value);
          var userData = {
            username: this.usernameRef.nativeElement.value,
            authData: token
          };
          window.sessionStorage.setItem('userData', JSON.stringify(userData));
        }
        else {
          alert('Invalid authentication');
        }

      });
  }

}
