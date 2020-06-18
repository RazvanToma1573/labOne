import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {NodeModel} from "./shared/node.model";

@Component({
  selector: 'app-node',
  templateUrl: './node.component.html',
  styleUrls: ['./node.component.css']
})
export class NodeComponent implements OnInit {
  private url = 'http://localhost:8080/api/nodes';
  nodes: NodeModel[] = [];
  showAdd = false;

  @ViewChild('nameref') nameRef: ElementRef;
  @ViewChild("capref") capRef: ElementRef;

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }
  ngOnInit(): void {
    this.getNodes();
  }

  getNodes() {
    this.httpClient.get<Array<NodeModel>>(this.url)
      .subscribe(nodes => {
        nodes.sort((a,b) => a.name.localeCompare(b.name));
        this.nodes = nodes;
      });
  }

  Add() {
    this.showAdd = true;
  }

  addNode() {
    var node = new NodeModel(this.nameRef.nativeElement.value, this.capRef.nativeElement.value);
    this.httpClient.post(this.url, node);
    this.showAdd = false;
  }

}
