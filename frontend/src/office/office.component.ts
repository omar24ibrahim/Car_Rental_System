import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";


@Component({
  selector: 'app-office',
  templateUrl: './office.component.html',
  styleUrls: ['./office.component.css'],
  standalone: true,
  imports: [CommonModule],
  providers: []
})
export class OfficeComponent {
  constructor() {}

  ngOnInit(): void {
  }


}