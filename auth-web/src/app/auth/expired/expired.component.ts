import { Router } from '@angular/router';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-expired',
  templateUrl: './expired.component.html',
  styleUrls: ['./expired.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ExpiredComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  reactivate() {
    this.router.navigate(['forgot'])
  }

}
