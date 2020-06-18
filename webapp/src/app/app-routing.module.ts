import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {NodeComponent} from "./node/node.component";


const routes: Routes = [
  {path: '', component: NodeComponent}
  //{path: 'nodes', component: NodeComponent, children: [
    //  {path: 'details/:id', component: NodeComponent}
    //]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
