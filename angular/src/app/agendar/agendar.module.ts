import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { SharedModule } from '../shared/shared.module';
import { AgendarRoutingModule } from './agendar-routing.module';
import { AgendarComponent } from './agendar/agendar.component';




@NgModule({
  declarations: [
    AgendarComponent
  ],
  imports: [
    CommonModule,
    AgendarRoutingModule,
    AppMaterialModule,
    SharedModule
  ]
})
export class AgendarModule { }
