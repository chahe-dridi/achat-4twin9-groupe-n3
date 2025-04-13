import { Component, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Operateur } from '../shared/Model/Operateur';
import { OperateurService } from '../shared/Service/Operateur.service';

@Component({
  selector: 'app-operateur',
  templateUrl: './operateur.component.html',
  styleUrls: ['./operateur.component.css']
})
export class OperateurComponent implements OnInit {

  listOperateurs: any;
  form: boolean = false;
  operateur!: Operateur;
  closeResult!: string;

  constructor(private operateurService: OperateurService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    console.log("Component initialized, fetching operateurs...");
    this.getAllOperateurs();
    this.operateur = {
      idOperateur: null,
      nom: null,
      prenom: null,
      password: null
    }
  }

  getAllOperateurs() {
    console.log("Fetching all operateurs...");
    this.operateurService.getAllOperateurs().subscribe(res => {
      console.log("Operateurs fetched successfully", res);
      this.listOperateurs = res;
    }, error => {
      console.error("Error fetching operateurs", error);
    });
  }

  addOperateur(o: any) {
    console.log("Adding operateur", o);
    this.operateurService.addOperateur(o).subscribe(() => {
      console.log("Operateur added successfully");
      this.getAllOperateurs();
      this.form = false;
    }, error => {
      console.error("Error adding operateur", error);
    });
  }

  editOperateur(operateur: Operateur) {
    console.log("Editing operateur", operateur);
    this.operateurService.editOperateur(operateur).subscribe(() => {
      console.log("Operateur edited successfully");
    }, error => {
      console.error("Error editing operateur", error);
    });
  }

  deleteOperateur(idOperateur: any) {
    console.log("Deleting operateur with id", idOperateur);
    this.operateurService.deleteOperateur(idOperateur).subscribe(() => {
      console.log("Operateur deleted successfully");
      this.getAllOperateurs();
    }, error => {
      console.error("Error deleting operateur", error);
    });
  }

  open(content: any, action: any) {
    if (action != null) {
      console.log("Opening modal for editing operateur", action);
      this.operateur = action;
    } else {
      console.log("Opening modal for adding new operateur");
      this.operateur = new Operateur();
    }
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  cancel() {
    console.log("Canceling form");
    this.form = false;
  }
}
