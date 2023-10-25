import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { SharedModule } from '../shared/shared.module';
import { AgendarRoutingModule } from './agendar-routing.module';
import { AgendarComponent } from './agendar/agendar.component';
import { TransferFormComponent } from './transfer-form/transfer-form.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AgendarComponent,
    TransferFormComponent
  ],
  imports: [
    CommonModule,
    AgendarRoutingModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule
  ]
})
export class AgendarModule { }
