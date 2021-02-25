import { PolymerElement } from '@polymer/polymer/polymer-element';
import { html } from '@polymer/polymer/lib/utils/html-tag';
import '@vaadin/vaadin-split-layout';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-grid/vaadin-grid-column';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-text-field';
import '@vaadin/vaadin-date-picker';
import '@vaadin/vaadin-button';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';

class GenerateMailView extends PolymerElement {
  _attachDom(dom) {
    // Do not use a shadow root
    this.appendChild(dom);
  }

  static get template() {
    return html`
      <vaadin-vertical-layout style="width: 100%; height: 100%;">
        <div style="width:400px;display:flex;flex-direction:column;">
          <div style="padding:var(--lumo-space-l);flex-grow:1;">
            <vaadin-form-layout>
              <label id="currentmail"></label>
              <vaadin-email-field id="emailfield"></vaadin-email-field>
              <vaadin-button theme="primary" id="savebtn">Zapisz</vaadin-button>
            </vaadin-form-layout>
          </div>
        </div>
      </vaadin-vertical-layout>
    `;
  }

  static get is() {
    return 'generatemail-view';
  }
}

customElements.define(GenerateMailView.is, GenerateMailView);
