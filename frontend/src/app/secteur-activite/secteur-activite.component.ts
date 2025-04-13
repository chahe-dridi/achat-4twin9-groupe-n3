import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SecteurActivite } from '../shared/Model/Secteur-activite';
import { SecteurActiviteService } from '../shared/Service/Secteur-activite.service';
import { LoggingService } from '../logging.service'; // Import the logging service

@Component({
  selector: 'app-secteur-activite',
  templateUrl: './secteur-activite.component.html',
  styleUrls: ['./secteur-activite.component.css']
})
export class SecteurActiviteComponent implements OnInit {

  listSec: any;
  form: boolean = false;
  sec!: SecteurActivite;
  closeResult!: string;

  constructor(
    private secteurActiviteService: SecteurActiviteService,
    private modalService: NgbModal,
    private loggingService: LoggingService // Inject logging service
  ) {}

  ngOnInit(): void {
    this.getAllSec();
    this.sec = {
      idSecteurActivite: null,
      codeSecteurActivite: null,
      libelleSecteurActivite: null
    };
    this.loggingService.logInfo('SecteurActiviteComponent initialized');
  }

  getAllSec() {
    this.loggingService.logInfo('Fetching all secteurs activites');
    this.secteurActiviteService.getAllSecteurActivites().subscribe(res => {
      this.listSec = res;
      this.loggingService.logInfo('Fetched secteurs activites successfully');
    }, error => {
      this.loggingService.logError('Error fetching secteurs activites: ' + error);
    });
  }

  addSec(p: any) {
    this.loggingService.logInfo('Adding secteur activite: ' + JSON.stringify(p));
    this.secteurActiviteService.addSecteurActivite(p).subscribe(() => {
      this.getAllSec();
      this.form = false;
      this.loggingService.logInfo('Secteur activite added successfully');
    }, error => {
      this.loggingService.logError('Error adding secteur activite: ' + error);
    });
  }

  editSec(sec: SecteurActivite) {
    this.loggingService.logInfo('Editing secteur activite: ' + JSON.stringify(sec));
    this.secteurActiviteService.editSecteurActivite(sec).subscribe(() => {
      this.loggingService.logInfo('Secteur activite edited successfully');
    }, error => {
      this.loggingService.logError('Error editing secteur activite: ' + error);
    });
  }

  deleteSec(idSec: any) {
    this.loggingService.logInfo('Deleting secteur activite with ID: ' + idSec);
    this.secteurActiviteService.deleteSecteurActivite(idSec).subscribe(() => {
      this.getAllSec();
      this.loggingService.logInfo('Secteur activite deleted successfully');
    }, error => {
      this.loggingService.logError('Error deleting secteur activite: ' + error);
    });
  }

  open(content: any, action: any) {
    if (action != null) {
      this.sec = action;
      this.loggingService.logInfo('Opening modal for editing secteur activite: ' + JSON.stringify(action));
    } else {
      this.sec = new SecteurActivite();
      this.loggingService.logInfo('Opening modal for adding new secteur activite');
    }
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
        this.loggingService.logInfo('Modal closed with result: ' + result);
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        this.loggingService.logInfo('Modal dismissed with reason: ' + reason);
      }
    );
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
    this.form = false;
    this.loggingService.logInfo('Cancel button clicked, form is reset');
  }
}
